//package com.mattae.simal.modules.base.integration.module;
//
//import com.blazebit.persistence.integration.jackson.EntityViewAwareObjectMapper;
//import com.blazebit.persistence.view.EntityViewManager;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.foreach.across.test.AcrossTestConfiguration;
//import com.foreach.across.test.AcrossWebAppConfiguration;
//import com.mattae.simal.modules.base.BaseModule;
//import com.mattae.simal.modules.base.domain.entities.Module;
//import com.mattae.simal.modules.base.domain.repositories.ModuleRepository;
//import com.mattae.simal.modules.base.domain.repositories.TranslationsRepository;
//import com.mattae.simal.modules.base.module.ModuleConfigProcessor;
//import com.mattae.simal.modules.base.yml.ModuleConfig;
//import com.mattae.simal.modules.base.yml.Translation;
//import io.github.benas.randombeans.EnhancedRandomBuilder;
//import io.github.benas.randombeans.api.EnhancedRandom;
//import io.github.benas.randombeans.randomizers.range.DoubleRangeRandomizer;
//import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
//import io.github.benas.randombeans.randomizers.text.StringRandomizer;
//import io.github.glytching.junit.extension.random.Random;
//import io.github.glytching.junit.extension.random.RandomBeansExtension;
//import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith({SpringExtension.class})
//@DirtiesContext
//@ContextConfiguration
//@AcrossWebAppConfiguration
//@AutoConfigureEmbeddedDatabase(beanName = "acrossDataSource", provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
//@TestExecutionListeners({
//    DependencyInjectionTestExecutionListener.class,
//    DirtiesContextTestExecutionListener.class,
//    TransactionalTestExecutionListener.class
//})
//@Slf4j
//public class TestModuleConfigProcessor {
//    static EnhancedRandom enhancedRandom = EnhancedRandomBuilder
//        .aNewEnhancedRandomBuilder()
//        .stringLengthRange(10, 20)
//        .randomize(Integer.class, IntegerRangeRandomizer.aNewIntegerRangeRandomizer(0, 10))
//        .randomize(String.class, StringRandomizer.aNewStringRandomizer(6))
//        .randomize(Double.class, DoubleRangeRandomizer.aNewDoubleRangeRandomizer(0.0, 10.0))
//        .collectionSizeRange(2, 3)
//        .objectPoolSize(30)
//        .build();
//    @RegisterExtension
//    static RandomBeansExtension randomBeansExtension = new RandomBeansExtension(enhancedRandom);
//    @Autowired
//    ModuleRepository moduleRepository;
//    @Autowired
//    TranslationsRepository translationsRepository;
//    @Random(excludes = {"id", "data", "file", "webRemotes", "menus", "webComponents"})
//    Module module;
//    @Autowired
//    private ModuleConfigProcessor configProcessor;
//
//    @BeforeEach
//    public void setup() {
//        translationsRepository.deleteAll();
//        moduleRepository.deleteAll();
//    }
//
//    @Test
//    @Transactional
//    public void testProcessConfigTranslation() {
//        moduleRepository.save(module);
//        ModuleConfig config = new ModuleConfig();
//        Translation translation = new Translation();
//        translation.setLang("en");
//        translation.setPath("lang.json");
//        config.setTranslations(List.of(translation));
//
//        configProcessor.processConfig(config, module);
//        assertEquals(1, translationsRepository.count());
//    }
//
//    @Configuration
//    @AcrossTestConfiguration(modules = BaseModule.NAME, modulePackages = "com.mattae.simal.modules", expose = {EntityViewManager.class, EntityManager.class, ObjectMapper.class, EntityViewAwareObjectMapper.class})
//    @PropertySource(value = "classpath:across-test.properties")
//    static class Config {
//
//    }
//}
