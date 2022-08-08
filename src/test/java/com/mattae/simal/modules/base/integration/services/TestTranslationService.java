//package com.mattae.simal.modules.base.integration.services;
//
//import com.blazebit.persistence.integration.jackson.EntityViewAwareObjectMapper;
//import com.blazebit.persistence.view.EntityViewManager;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.foreach.across.test.AcrossTestConfiguration;
//import com.foreach.across.test.AcrossWebAppConfiguration;
//import com.mattae.simal.modules.base.BaseModule;
//import com.mattae.simal.modules.base.domain.entities.Translation;
//import com.mattae.simal.modules.base.domain.repositories.TranslationsRepository;
//import com.mattae.simal.modules.base.services.TranslationService;
//import io.github.benas.randombeans.EnhancedRandomBuilder;
//import io.github.benas.randombeans.api.EnhancedRandom;
//import io.github.benas.randombeans.randomizers.range.DoubleRangeRandomizer;
//import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
//import io.github.benas.randombeans.randomizers.text.StringRandomizer;
//import io.github.glytching.junit.extension.random.Random;
//import io.github.glytching.junit.extension.random.RandomBeansExtension;
//import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.beans.BeanUtils;
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
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
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
//public class TestTranslationService {
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
//    TranslationsRepository translationsRepository;
//    @Autowired
//    TranslationService translationService;
//    @Random(excludes = {"id", "data", "module"})
//    private Translation translation;
//
//    @BeforeEach
//    public void setup() throws JsonProcessingException {
//        String json = "{\n" +
//            "    \"EHR\": {\n" +
//            "        \"PATIENT\": {\n" +
//            "            \"RHESUS\": \"Rhesus\",\n" +
//            "            \"HB\": \"HB\",\n" +
//            "            \"EDUCATION\": \"Education\",\n" +
//            "            \"OCCUPATION\": \"Occupation\",\n" +
//            "            \"GENERAL_INFORMATION\": \"General information\",\n" +
//            "            \"GENERAL_INFORMATION_PLACEHOLDER\": \"General information\",\n" +
//            "            \"MENU\": {\n" +
//            "                \"PATIENTS\": \"Patients\"\n" +
//            "            }\n" +
//            "        }\n" +
//            "    }\n" +
//            "}";
//        translation.setData(new ObjectMapper().readTree(json));
//
//        translationsRepository.deleteAll();
//    }
//
//    @Test
//    @Transactional
//    public void testSave() {
//        Translation translation = translationService.save(this.translation);
//        assertNotNull(translation.getId());
//    }
//
//    @Test
//    @Transactional
//    public void testGetById() {
//        translation = translationsRepository.save(translation);
//        Optional<Translation> result = translationService.getById(translation.getId());
//        assertTrue(result.isPresent());
//        assertEquals(translation.getId(), result.get().getId());
//    }
//
//    @Test
//    @Transactional
//    public void testDeleteById() {
//        translation = translationsRepository.save(translation);
//        assertEquals(1, translationsRepository.count());
//        translationService.deleteById(translation.getId());
//        assertEquals(0, translationsRepository.count());
//    }
//
//    @Transactional
//    @Test
//    public void testListByLang() throws JsonProcessingException {
//        translation.setOrder(2);
//        translation = translationsRepository.save(translation);
//        Translation translation2 = new Translation();
//        BeanUtils.copyProperties(translation, translation2, "id");
//        String json = "{\n" +
//            "    \"EHR\": {\n" +
//            "        \"PATIENT\": {\n" +
//            "            \"MENU\": {\n" +
//            "                \"PATIENTS\": \"Clients\",\n" +
//            "                \"PATIENTS_SETTINGS\": \"Clients Setting\"\n" +
//            "            }\n" +
//            "        }\n" +
//            "    }\n" +
//            "}";
//        translation2.setData(new ObjectMapper().readTree(json));
//        translation2.setOrder(3);
//        translationsRepository.save(translation2);
//        JsonNode trans = translationService.listByLang(translation.getLang());
//
//        assertNotNull(trans);
//        assertEquals("Clients", trans.get("EHR").get("PATIENT").get("MENU").get("PATIENTS").asText());
//        assertEquals("Rhesus", trans.get("EHR").get("PATIENT").get("RHESUS").asText());
//        assertEquals("Clients Setting", trans.get("EHR").get("PATIENT").get("MENU").get("PATIENTS_SETTINGS").asText());
//    }
//
//    @Configuration
//    @AcrossTestConfiguration(modules = BaseModule.NAME, modulePackages = "com.mattae.simal.modules", expose = {EntityViewManager.class, EntityManager.class, ObjectMapper.class, EntityViewAwareObjectMapper.class})
//    @PropertySource(value = "classpath:across-test.properties")
//    static class Config {
//
//    }
//}
