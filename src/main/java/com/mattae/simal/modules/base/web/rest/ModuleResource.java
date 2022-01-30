package com.mattae.simal.modules.base.web.rest;

import com.mattae.simal.modules.base.domain.entities.Menu;
import com.mattae.simal.modules.base.domain.entities.Module;
import com.mattae.simal.modules.base.domain.entities.WebRemote;
import com.mattae.simal.modules.base.domain.repositories.MenuRepository;
import com.mattae.simal.modules.base.domain.repositories.ModuleRepository;
import com.mattae.simal.modules.base.domain.repositories.WebRemoteRepository;
import com.mattae.simal.modules.base.services.dto.ModuleDependencyDTO;
import com.mattae.simal.modules.base.services.ModuleService;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.mattae.simal.modules.base.services.MenuService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ModuleResource {
    private final MenuRepository menuRepository;
    private final ModuleRepository moduleRepository;
    private final WebRemoteRepository webRemoteRepository;
    private final ModuleService moduleService;
    private final MenuService menuService;

    @GetMapping("/modules/{id}")
    @Timed
    public ResponseEntity<Module> getModule(@PathVariable("id") UUID id) {
        LOG.debug("Getting module: {}", id);

        return ResponseUtil.wrapOrNotFound(moduleService.getModule(id));
    }

    @GetMapping("/modules")
    @Timed
    @Cacheable("modules")
    public List<Module> getWebModules() {
        LOG.debug("Getting all modules");
        return moduleService.getModules();
    }

    @PostMapping("/modules/activate")
    public Module activateModule(@RequestBody Module module) {
        return moduleService.activate(module);
    }

    @PostMapping("/modules/deactivate")
    public Module deactivateModule(@RequestBody Module module) {
        return moduleService.deactivate(module);
    }

    @GetMapping("/modules/{id}/uninstall")
    @CacheEvict({"modules"})
    public void uninstallModule(@PathVariable UUID id) {
        moduleService.uninstall(id);
    }

    @PostMapping("/modules/update")
    public Module updateModule(@RequestBody Module module) {
        return moduleService.installOrUpdate(module);
    }

    @PostMapping("/modules/upload")
    public Module uploadModuleData(@RequestParam("file") MultipartFile file) {
        return moduleService.uploadModuleData(file);
    }

    @PostMapping("/modules/install")
    @CacheEvict({"modules"})
    public Module installModule(final @RequestBody Module module) {
        return moduleService.installOrUpdate(module);
    }

    @GetMapping("/modules/{id}/dependencies")
    public List<ModuleDependencyDTO> getDependencies(@PathVariable UUID id) {
        return moduleService.getDependencies(id);
    }

    @GetMapping("/modules/menus")
    public List<Menu> getMenus() {
        LOG.debug("Getting all menus for current user");
        return menuService.getMenu();
    }

    @GetMapping("/modules/web-remotes")
    @Cacheable(cacheNames = "webRemotes")
    public List<WebRemote> getWebRemotes() {
        return moduleRepository.findByStartedIsTrue().stream()
            .flatMap(module -> webRemoteRepository.findByModule(module).stream())
            .collect(Collectors.toList());
    }
}
