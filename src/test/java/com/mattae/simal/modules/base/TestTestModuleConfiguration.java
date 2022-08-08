//package com.mattae.simal.modules.base;
//
//import com.mattae.simal.modules.base.yml.ConfigSchemaValidator;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//import java.nio.file.Files;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@Slf4j
//public class TestTestModuleConfiguration {
//
//
//    @Test
//    public void testConfigFile() throws IOException {
//        String config = new String(Files.readAllBytes(new ClassPathResource("module.yml").getFile().toPath()));
//        assertTrue(ConfigSchemaValidator.isValid(config), "Configuration file is not correct");
//    }
//}
