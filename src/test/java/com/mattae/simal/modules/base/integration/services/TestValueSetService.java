//package com.mattae.simal.modules.base.integration.services;
//
//import com.blazebit.persistence.integration.jackson.EntityViewAwareObjectMapper;
//import com.blazebit.persistence.view.ConvertOption;
//import com.blazebit.persistence.view.EntityViewManager;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.foreach.across.test.AcrossTestConfiguration;
//import com.foreach.across.test.AcrossWebAppConfiguration;
//import com.mattae.simal.modules.base.BaseModule;
//import com.mattae.simal.modules.base.domain.entities.ValueSet;
//import com.mattae.simal.modules.base.domain.repositories.ValueSetRepository;
//import com.mattae.simal.modules.base.services.ValueSetService;
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
//import java.util.List;
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
//public class TestValueSetService {
//    static EnhancedRandom enhancedRandom = EnhancedRandomBuilder
//        .aNewEnhancedRandomBuilder()
//        .stringLengthRange(10, 20)
//        .randomize(Integer.class, IntegerRangeRandomizer.aNewIntegerRangeRandomizer(0, 10))
//        .randomize(String.class, StringRandomizer.aNewStringRandomizer(8))
//        .randomize(Double.class, DoubleRangeRandomizer.aNewDoubleRangeRandomizer(0.0, 10.0))
//        .collectionSizeRange(2, 3)
//        .objectPoolSize(30)
//        .build();
//    @RegisterExtension
//    static RandomBeansExtension randomBeansExtension = new RandomBeansExtension(enhancedRandom);
//
//    @Autowired
//    ValueSetService valueSetService;
//    @Autowired
//    ValueSetRepository valueSetRepository;
//    @Autowired
//    EntityViewManager evm;
//    @Random(excludes = {"id"})
//    ValueSet valueSet;
//
//    @BeforeEach
//    @Transactional
//    public void setup() {
//        valueSetRepository.deleteAll();
//    }
//
//    @Test
//    public void testSave() {
//        valueSet.setLang("en");
//        ValueSet.UpdateView value = evm.convert(valueSet, ValueSet.UpdateView.class, ConvertOption.CREATE_NEW);
//
//        assertNull(value.getId());
//        valueSetService.saveValue(value);
//        assertNotNull(value.getId());
//    }
//
//    @Test
//    public void testSaveValues() {
//        valueSet.setLang("en");
//        ValueSet.UpdateView value = evm.convert(valueSet, ValueSet.UpdateView.class, ConvertOption.CREATE_NEW);
//
//        List<ValueSet.UpdateView> values = valueSetService.saveValues(List.of(value));
//        assertEquals(1, values.size());
//        assertEquals(value.getCode(), values.get(0).getCode());
//    }
//
//    @Test
//    public void testGetById() {
//        valueSet.setLang("en");
//        valueSet = valueSetRepository.save(valueSet);
//        Optional<ValueSet.BaseView> result = valueSetService.getById(valueSet.getId());
//        assertTrue(result.isPresent());
//        assertEquals(valueSet.getId(), result.get().getId());
//    }
//
//    @Test
//    public void testGetDisplay() {
//        valueSet.setLang(null);
//        valueSetRepository.save(valueSet);
//        String display = valueSetService.getDisplay(valueSet.getType(), valueSet.getProvider(), valueSet.getLang(),
//            valueSet.getCode());
//        assertEquals(valueSet.getDisplay(), display);
//        display = valueSetService.getDisplay(valueSet.getType(), valueSet.getProvider(), valueSet.getLang(),
//            "A".repeat(30));
//        assertEquals(0, display.length());
//    }
//
//    @Test
//    public void testGetValues() {
//        valueSet.setLang(null);
//        valueSetRepository.save(valueSet);
//
//        List<ValueSet.BaseView> values = valueSetService.getValues(valueSet.getType(), valueSet.getProvider(), null,
//            valueSet.getLang());
//        assertEquals(1, values.size());
//        values = valueSetService.getValues(valueSet.getType(), valueSet.getProvider(), valueSet.getActive(),
//            valueSet.getLang());
//        assertEquals(1, values.size());
//        values = valueSetService.getValues("a".repeat(30), valueSet.getProvider(), valueSet.getActive(),
//            valueSet.getLang());
//        assertEquals(0, values.size());
//    }
//
//    @Configuration
//    @AcrossTestConfiguration(modules = BaseModule.NAME, modulePackages = "com.mattae.simal.modules", expose = {EntityViewManager.class, EntityManager.class, ObjectMapper.class, EntityViewAwareObjectMapper.class})
//    @PropertySource(value = "classpath:across-test.properties")
//    static class Config {
//
//    }
//}
