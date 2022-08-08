//package com.mattae.simal.modules.base.integration.web;
//
//import com.blazebit.persistence.integration.jackson.EntityViewAwareObjectMapper;
//import com.blazebit.persistence.view.ConvertOption;
//import com.blazebit.persistence.view.EntityViewManager;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.foreach.across.test.AcrossTestConfiguration;
//import com.foreach.across.test.AcrossWebAppConfiguration;
//import com.mattae.simal.modules.base.BaseModule;
//import com.mattae.simal.modules.base.domain.entities.ValueSet;
//import com.mattae.simal.modules.base.services.ValueSetService;
//import com.mattae.simal.modules.base.web.rest.ValueSetResource;
//import io.github.benas.randombeans.EnhancedRandomBuilder;
//import io.github.benas.randombeans.api.EnhancedRandom;
//import io.github.benas.randombeans.randomizers.range.DoubleRangeRandomizer;
//import io.github.benas.randombeans.randomizers.range.LongRangeRandomizer;
//import io.github.benas.randombeans.randomizers.text.StringRandomizer;
//import io.github.glytching.junit.extension.random.Random;
//import io.github.glytching.junit.extension.random.RandomBeansExtension;
//import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.notNullValue;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith({SpringExtension.class})
//@DirtiesContext
//@ContextConfiguration
//@AcrossWebAppConfiguration
//@AutoConfigureEmbeddedDatabase(beanName = "acrossDataSource", provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
//@TestExecutionListeners({
//    DependencyInjectionTestExecutionListener.class,
//    DirtiesContextTestExecutionListener.class,
//    TransactionalTestExecutionListener.class,
//    WithSecurityContextTestExecutionListener.class,
//    MockitoTestExecutionListener.class
//})
//public class TestValueSetResource {
//    private static final String BASE_URL = "/api/value-sets/";
//    static EnhancedRandom enhancedRandom = EnhancedRandomBuilder
//        .aNewEnhancedRandomBuilder()
//        .stringLengthRange(10, 20)
//        .randomize(Long.class, LongRangeRandomizer.aNewLongRangeRandomizer(0L, 10L))
//        .randomize(String.class, StringRandomizer.aNewStringRandomizer(8))
//        .randomize(Double.class, DoubleRangeRandomizer.aNewDoubleRangeRandomizer(0.0, 10.0))
//        .collectionSizeRange(2, 3)
//        .objectPoolSize(30)
//        .build();
//    @RegisterExtension
//    static RandomBeansExtension randomBeansExtension = new RandomBeansExtension(enhancedRandom);
//    @Autowired
//    EntityViewManager evm;
//    @Autowired
//    ObjectMapper objectMapper;
//    @Random
//    ValueSet valueSet;
//    ValueSet.UpdateView updateView;
//    private MockMvc mvc;
//    @Autowired
//    private List<HttpMessageConverter<?>> messageConverters;
//    @Mock
//    private ValueSetService valueSetService;
//    @InjectMocks
//    private ValueSetResource valueSetResource;
//
//    @BeforeEach
//    public void setup() {
//        updateView = evm.convert(valueSet, ValueSet.UpdateView.class, ConvertOption.CREATE_NEW);
//        this.mvc = MockMvcBuilders.standaloneSetup(valueSetResource)
//            .setMessageConverters(messageConverters.toArray(new HttpMessageConverter[0])).build();
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testGetById() throws Exception {
//        given(valueSetService.getById(valueSet.getId())).willReturn(Optional.of(updateView));
//
//        mvc.perform(get(BASE_URL + "{id}", valueSet.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id", notNullValue()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testGetByIdNotFound() throws Exception {
//        given(valueSetService.getById(anyLong())).willReturn(Optional.empty());
//
//        mvc.perform(get(BASE_URL + "{id}", Integer.MAX_VALUE)
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testGetDisplayText() throws Exception {
//        when(valueSetService.getDisplay(anyString(), anyString(), anyString(), isNull())).thenReturn(valueSet.getDisplay());
//
//        mvc.perform(get(BASE_URL + "display-text/type/{type}/provider/{provider}/value/{value}", "a", "a", "a")
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(content().string(valueSet.getDisplay()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testGetValues() throws Exception {
//        when(valueSetService.getValues(anyString(), anyString(), anyBoolean(), isNull()))
//            .thenReturn(List.of(updateView));
//
//        mvc.perform(get(BASE_URL + "/provider/{provider}/type/{type}", "a", "a")
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testCreate() throws Exception {
//        when(valueSetService.saveValue(any(ValueSet.BaseView.class))).thenReturn(updateView);
//
//        mvc.perform(post(BASE_URL)
//                .content(objectMapper.writeValueAsString(valueSet))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id", notNullValue()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testUpdate() throws Exception {
//        when(valueSetService.saveValue(any(ValueSet.BaseView.class))).thenReturn(updateView);
//
//        mvc.perform(put(BASE_URL)
//                .content(objectMapper.writeValueAsString(valueSet))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id", notNullValue()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testCreateMulti() throws Exception {
//        when(valueSetService.saveValues(anyList())).thenReturn(List.of(updateView));
//
//        mvc.perform(post(BASE_URL + "multi")
//                .content(objectMapper.writeValueAsString(List.of(valueSet)))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Configuration
//    @AcrossTestConfiguration(modules = BaseModule.NAME, modulePackages = "com.mattae.simal.modules",
//        expose = {EntityViewManager.class, EntityManager.class, ObjectMapper.class, HttpMessageConverter.class})
//    @PropertySource(value = "classpath:across-test.properties")
//    static class Config {
//
//    }
//}
