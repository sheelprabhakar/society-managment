package com.c4c.housing.rest;

import com.c4c.housing.HousingSocietyApplication;
import com.c4c.housing.config.security.JwtTokenProvider;
import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.utils.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = HousingSocietyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    String token = "";
    @Container
    private static final MySQLContainer database = new MySQLContainer<>(DockerImageName.parse("mysql:8.1.0"));

    //private static final GenericContainer memcached = new GenericContainer(DockerImageName.parse("memcached:1.6.23"));
    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        // Test container database
        registry.add("spring.datasource.url",database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.flyway.url",database::getJdbcUrl);
        registry.add("spring.flyway.schemas",database::getDatabaseName);
        registry.add("spring.flyway.defaultSchema",database::getDatabaseName);
        registry.add("spring.flyway.user", database::getUsername);
        registry.add("spring.flyway.password", database::getPassword);

        //Test container memcached
        /*registry.add("memcached.cache.servers",() -> String.format("%s:%d", memcached.getHost(), memcached.getExposedPorts().get(0)));
        registry.add("memcached.cache.expirations", ()->Integer.valueOf(3600));
        registry.add("memcached.cache.operation-timeout", ()->Integer.valueOf(3000));
        registry.add("memcached.cache.provider", ()->String.valueOf("static"));*/
    }


    String getAdminToken() throws Exception {
        if(!StringUtils.hasLength(token)) {
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/auth/authenticate")
                            .content("{\"username\":\"sheel.prabhakar@gmail.com\", \"password\":\"admin123\", \"isOtp\":false}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk()).andReturn();
            Map<String, String> map = new HashMap<>();
            map = TestUtils
                    .convertJsonStringToObject(mvcResult.getResponse()
                            .getContentAsString(), map.getClass());
            token = "Bearer " + map.get("token");
        }
        return token;

    }
}