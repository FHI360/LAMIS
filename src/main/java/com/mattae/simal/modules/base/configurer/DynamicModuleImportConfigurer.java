package com.mattae.simal.modules.base.configurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.foreach.across.config.AcrossContextConfigurer;
import com.foreach.across.core.AcrossContext;
import com.foreach.across.core.AcrossModule;
import com.foreach.across.modules.filemanager.FileManagerModuleSettings;
import com.github.zafarkhaja.semver.Version;
import com.mattae.simal.modules.base.configurer.util.BootstrapClassLoaderHolder;
import com.mattae.simal.modules.base.module.ModuleUtils;
import com.mattae.simal.modules.base.yml.ConfigSchemaValidator;
import com.mattae.simal.modules.base.yml.Dependency;
import com.mattae.simal.modules.base.yml.ModuleConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class DynamicModuleImportConfigurer implements AcrossContextConfigurer {
    private static final ObjectMapper MAPPER;
    public static String MODULE_PATH = "";

    static {
        MAPPER = new ObjectMapper(new YAMLFactory());
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            MODULE_PATH = Files.createTempDirectory("tmpModuleData").toFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final FileManagerModuleSettings fileManagerModuleSettings;

    private final List<String> validArtifacts = new ArrayList<>();
    private final List<String> validDependencies = new ArrayList<>();
    private final DataSource dataSource;
    private List<String> classNames = new ArrayList<>();
    private JdbcTemplate jdbcTemplate;

    @Override
    public void configure(AcrossContext context) {
        classNames.forEach(className -> {
            ClassLoader classLoader = BootstrapClassLoaderHolder.CLASS_LOADER;
            if (classLoader != null) {
                try {
                    Class<?> cls = classLoader.loadClass(className);
                    Constructor<?> ctor = cls.getConstructor();
                    context.addModule((AcrossModule) ctor.newInstance());
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                    IllegalAccessException | InvocationTargetException ignored) {
                }
            }
        });
    }

    private List<Module> getActiveModules() {
        return jdbcTemplate.query("select m.id, m.name, version, case when file_descriptor is null then m.name else " +
                "file_descriptor end  file from module m left join fmm_file_reference f on f.id = file_id where " +
                "active = true and uninstall = false",
            new BeanPropertyRowMapper<>(Module.class));
    }

    @SneakyThrows
    private boolean validArtifact(String moduleId) {
        ModuleArtifact moduleArtifact = getModuleArtifact(moduleId);
        if (moduleArtifact == null || moduleArtifact.data == null) {
            return false;
        }
        if (validArtifacts.contains(moduleArtifact.name)) {
            return true;
        }
        Path tmpFile = Files.createTempFile("", "jar");
        IOUtils.copy(new ByteArrayInputStream(moduleArtifact.data), new FileOutputStream(tmpFile.toFile()));
        URLClassLoader classLoader = new URLClassLoader(new URL[]{tmpFile.toUri().toURL()});
        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        ClassPathScanningCandidateComponentProvider provider =
            new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AcrossModule.class));
        Set<BeanDefinition> beans = provider.findCandidateComponents(moduleArtifact.basePackage);
        Thread.currentThread().setContextClassLoader(currentClassLoader);
        classLoader.close();
        boolean valid = !beans.isEmpty();

        ModuleConfig config;
        try {
            config = ModuleUtils.loadModuleConfig(new FileInputStream(tmpFile.toFile()), "module.yml");
        } catch (Exception e) {
            return false;
        }
        FileUtils.deleteQuietly(tmpFile.toFile());

        if (config != null) {
            String yaml = MAPPER.writeValueAsString(config);
            valid = valid && ConfigSchemaValidator.isValid(yaml);
        } else {
            valid = false;
        }
        if (valid) {
            String className = beans.iterator().next().getBeanClassName();
            classNames.add(className);
            validArtifacts.add(moduleArtifact.name);
            final Path moduleRuntimePath = Paths.get(MODULE_PATH, "runtime",
                StringUtils.replace(moduleArtifact.file, "\\", "/").replaceAll(":", "/"));
            FileUtils.touch(moduleRuntimePath.toFile());
            IOUtils.copy(new ByteArrayInputStream(moduleArtifact.data), new FileOutputStream(moduleRuntimePath.toFile()));
        }
        return valid;
    }

    private ModuleArtifact getModuleArtifact(String moduleId) {
        ModuleArtifact moduleArtifact = jdbcTemplate.queryForObject("" +
                "select m.name, case when file_descriptor is null then m.name else file_descriptor end file, base_package, " +
                "data from module m left join fmm_file_reference f on f.id = file_id where m.id = ?",
            new BeanPropertyRowMapper<>(ModuleArtifact.class), moduleId);
        if (moduleArtifact == null) {
            return null;
        }
        if (moduleArtifact.data == null) {
            if (moduleArtifact.file == null) {
                return null;
            }
            InputStream is;
            Path path = Paths.get(fileManagerModuleSettings.getLocalRepositoriesRoot(),
                StringUtils.replace(moduleArtifact.file, ":", "/"));
            try {
                is = path.toUri().toURL().openStream();
                moduleArtifact.data = IOUtils.toByteArray(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return moduleArtifact;
    }

    private void addClassPathURL(URL url) {
        ClassLoader classLoader = BootstrapClassLoaderHolder.CLASS_LOADER;

        if (classLoader != null) {
            try {
                Method method = Class.forName("org.springframework.boot.loader.LaunchedURLClassLoader")
                    .getMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(classLoader, url);
            } catch (Exception ignored) {
            }
        }
    }

    private boolean moduleDependenciesMet(Module module) {
        if (validDependencies.contains(module.name)) {
            return true;
        }
        ModuleArtifact artifact = getModuleArtifact(module.id);
        if (artifact != null && artifact.data != null) {
            try {
                ModuleConfig config = ModuleUtils.loadModuleConfig(new ByteArrayInputStream(artifact.data), "module.yml");
                if (config != null) {
                    List<Dependency> deps = config.getDependencies();
                    List<Map<String, Object>> dependencies = config.getDependencies().stream()
                        .flatMap(d -> jdbcTemplate.queryForList("select id, version installed, ? required, active, name from module where name = ?",
                            d.getVersion(), d.getName())
                            .stream())
                        .collect(Collectors.toList());
                    boolean valid = dependencies.stream()
                        .allMatch(dependency -> {
                            Boolean active = (Boolean) dependency.get("active");
                            try {
                                Version installed = Version.valueOf((String) dependency.get("installed"));
                                return installed.satisfies((String) dependency.get("required")) && active != null && active;
                            } catch (Exception e) {
                                return false;
                            }
                        }) && dependencies.stream()
                        .allMatch(dependency -> {
                            String id = dependency.get("id").toString();
                            String name = (String) dependency.get("name");
                            Module m = new Module();
                            m.id = id;
                            m.name = name;
                            return moduleDependenciesMet(m) && validArtifact(id);
                        });
                    if (valid) {
                        validDependencies.add(module.name);
                    }
                    return valid;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    List<Module> getResolvedModules() {
        return getActiveModules().stream()
            .filter(module -> {
                boolean valid = moduleDependenciesMet(module) && validArtifact(module.id);
                if (!valid) {
                    classNames = classNames.stream()
                        .filter(className -> !className.contains(module.name))
                        .collect(Collectors.toList());
                }
                return valid;
            })
            .collect(Collectors.toList());
    }

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.update("update module set started = false");
            getResolvedModules().forEach(module -> {
                String artifact = module.file;
                final Path moduleRuntimePath = Paths.get(MODULE_PATH, "runtime",
                    StringUtils.replace(artifact, "\\", "/").replaceAll(":", "/"));
                try {
                    addClassPathURL(moduleRuntimePath.toUri().toURL());
                } catch (Exception ignored) {
                }
            });
        } catch (Exception ignored) {
        }
    }

    @Data
    public static class ModuleArtifact {
        private String basePackage;
        private String file;
        private String name;
        private byte[] data;
    }

    @Data
    public static class Module {
        String id;
        String name;
        String file;
    }
}
