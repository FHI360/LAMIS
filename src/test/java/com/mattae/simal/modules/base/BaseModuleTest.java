//package com.mattae.simal.modules.base;
//
//import com.blazebit.persistence.CriteriaBuilderFactory;
//import com.blazebit.persistence.view.EntityViewManager;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.foreach.across.core.AcrossContext;
//import com.foreach.across.modules.filemanager.FileManagerModuleSettings;
//import com.foreach.across.modules.filemanager.services.FileManager;
//import com.foreach.across.test.AcrossTestConfiguration;
//import com.foreach.across.test.AcrossWebAppConfiguration;
//import com.github.springtestdbunit.DbUnitTestExecutionListener;
//import com.github.springtestdbunit.annotation.DbUnitConfiguration;
//import com.mattae.simal.modules.base.domain.entities.Address;
//import com.mattae.simal.modules.base.domain.entities.Party;
//import com.mattae.simal.modules.base.domain.repositories.PartyRepository;
//import com.mattae.simal.modules.base.domain.views.AddressView;
//import com.mattae.simal.modules.base.domain.views.PartyView;
//import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.graphql.GraphQlService;
//import org.springframework.graphql.test.tester.GraphQlTester;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import javax.persistence.EntityManager;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration
//@AcrossWebAppConfiguration
//@AutoConfigureEmbeddedDatabase(beanName = "acrossDataSource", provider = ZONKY)
//@TestExecutionListeners({
//    DependencyInjectionTestExecutionListener.class,
//    DirtiesContextTestExecutionListener.class,
//    TransactionalTestExecutionListener.class,
//    DbUnitTestExecutionListener.class
//})
//@DbUnitConfiguration(databaseConnection = "acrossDataSource")
//@Slf4j
//public class BaseModuleTest {
//    @Autowired
//    AcrossContext context;
//
//    @Autowired
//    FileManagerModuleSettings fileManagerModuleSettings;
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    GraphQlService graphQlService;
//
//    @Autowired
//    FileManager fileManager;
//    @Autowired
//    PartyRepository partyRepository;
//    @Autowired
//    EntityViewManager evm;
//    @Autowired
//    EntityManager em;
//    @Autowired
//    CriteriaBuilderFactory cbf;
//    @Autowired
//    TransactionTemplate transactionTemplate;
//    @Autowired
//    ObjectMapper objectMapper;
//    private GraphQlTester graphQlTester;
//
//    @Test
//    public void module_repository_should_exists() throws Exception {
//        Map<String, Object> body = new HashMap<>();
//        body.put("username", "admin");
//        body.put("password", "admin");
//        mockMvc.perform(post("/api/authenticate")
//                .content(new ObjectMapper().writeValueAsString(body))
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andDo(print())
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    @Transactional
//    void subscriptionWithEntityPath() {
//        graphQlTester = GraphQlTester.create(graphQlService);
//
//       /* Flux<String> result = this.graphQlTester.query("subscription { greetings }")
//            .executeSubscription()
//            .toFlux("greetings", String.class);
//
//        List<String> list = new ArrayList<>();
//        result.log().subscribe(e-> LOG.info("Element: {}", e));
//        LOG.info("List: {}", list);
//
//        Flux.just(1, 2, 3, 4)
//            .log()
//            .subscribe(e-> LOG.info("Element: {}", e));*/
//        transactionTemplate.execute(status -> {
//            Party party = new Party();
//            party.setDisplayName("Party");
//
//            party = partyRepository.save(party);
//            Address address = new Address();
//            address.setLine1("line1");
//            address.setLine2("line2");
//            address.setCity("city");
//            address.setAddressType("Residential");
//            address.setState("state");
//            address.setParty(party);
//
//            Address address2 = new Address();
//            address2.setLine1("line11");
//            address2.setLine2("line22");
//            address2.setCity("city2");
//            address2.setAddressType("Residential");
//            address2.setState("state2");
//            address2.setParty(party);
//            party.setAddresses(Set.of(address, address2));
//
//            Party party1 = partyRepository.findAll().get(1);
//
//            Address address3 = new Address();
//            address3.setLine1("line13");
//            address3.setLine2("line23");
//            address3.setCity("city3");
//            address3.setAddressType("Residential");
//            address3.setState("state3");
//
//            PartyView pv = evm.find(em, PartyView.class, party.getId());
//            pv.getAddresses().remove(pv.getAddresses().iterator().next());
//            AddressView a = objectMapper.convertValue(address3, AddressView.class);
//            pv.getAddresses().add(a);
//            evm.save(em, pv);
//            LOG.info("Address: {}", pv.getAddresses());
//            return null;
//        });
//    }
//
//    @SneakyThrows
//    @Test
//    public void contextShouldContainBaseModule() {
//        assertNotNull(context.getModule(BaseModule.NAME));
//    }
//
//    @AcrossTestConfiguration(modules = BaseModule.NAME, expose = {FileManagerModuleSettings.class,
//        GraphQlService.class, EntityViewManager.class, EntityManager.class, ObjectMapper.class})
//    @PropertySource("classpath:across-test.properties")
//    @EnableTransactionManagement
//    static class Config {
//    }
//}
