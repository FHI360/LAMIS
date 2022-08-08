//package com.mattae.simal.modules.base.integration.web;
//
//import com.blazebit.persistence.view.EntityViewManager;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.foreach.across.test.AcrossTestConfiguration;
//import com.foreach.across.test.AcrossWebAppConfiguration;
//import com.mattae.simal.modules.base.BaseModule;
//import com.mattae.simal.modules.base.domain.entities.Translation;
//import com.mattae.simal.modules.base.services.TranslationService;
//import com.mattae.simal.modules.base.web.errors.RestResponseEntityExceptionHandler;
//import com.mattae.simal.modules.base.web.rest.TranslationResource;
//import io.github.glytching.junit.extension.random.Random;
//import io.github.glytching.junit.extension.random.RandomBeansExtension;
//import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.BeanUtils;
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
//import static org.hamcrest.Matchers.notNullValue;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith({SpringExtension.class, RandomBeansExtension.class})
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
//public class TestTranslationResource {
//    private static final String BASE_URL = "/api/translations/";
//    @Autowired
//    RestResponseEntityExceptionHandler entityExceptionHandler;
//    private MockMvc mvc;
//    @Autowired
//    private List<HttpMessageConverter<?>> messageConverters;
//    @Mock
//    private TranslationService translationService;
//    @InjectMocks
//    private TranslationResource translationResource;
//    @Random(excludes = {"data"})
//    private Translation translation;
//
//    @BeforeEach
//    public void setup() {
//        ObjectNode data = new ObjectMapper().createObjectNode();
//        data.put("name", "test");
//        translation.setData(data);
//        this.mvc = MockMvcBuilders.standaloneSetup(translationResource, entityExceptionHandler)
//            .setMessageConverters(messageConverters.toArray(new HttpMessageConverter[0]))
//            .build();
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testGetById() throws Exception {
//        given(translationService.getById(anyLong())).willReturn(Optional.of(translation));
//
//        mvc.perform(get(BASE_URL + "{id}", translation.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id", notNullValue()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testSave() throws Exception {
//        given(translationService.save(any(Translation.class))).willReturn(translation);
//
//        Translation translation = new Translation();
//        BeanUtils.copyProperties(this.translation, translation, "id", "data");
//        mvc.perform(post(BASE_URL)
//                .content(new ObjectMapper().writeValueAsString(translation))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id", notNullValue()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testUpdate() throws Exception {
//        given(translationService.save(any(Translation.class))).willReturn(translation);
//
//        mvc.perform(put(BASE_URL)
//                .content(new ObjectMapper().writeValueAsString(translation))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id", notNullValue()));
//    }
//
//    @Test
//    @WithMockUser(username = "admin")
//    public void testDeleteById() throws Exception {
//        doNothing().when(translationService).deleteById(anyLong());
//
//        mvc.perform(delete(BASE_URL + "{id}", translation.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testListByLang() throws Exception {
//        given(translationService.listByLang(anyString())).willReturn(translation.getData());
//
//        mvc.perform(get(BASE_URL + "lang/{lang}", translation.getLang())
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(content().json(translation.getData().toString()));
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
