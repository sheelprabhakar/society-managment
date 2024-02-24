package com.c4c.housing.rest;

import com.c4c.housing.HousingSocietyApplication;
import com.c4c.housing.config.security.JwtTokenProvider;
import com.c4c.housing.rest.resource.auth.JwtResponse;
import com.c4c.housing.utils.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Abstract integration test.
 */
@SpringBootTest(classes = HousingSocietyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractIntegrationTest {
    /**
     * The Mock mvc.
     */
    @Autowired
    MockMvc mockMvc;

    @Value("${security.jwt.token.secret-key:secret-key}")
    String secretKey;

    /**
     * The Jwt token provider.
     */
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    /**
     * The Token.
     */
    String token = "";
    /**
     * The constant database.
     */
    @Container
    private static final MySQLContainer database = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.1.0"));

    /**
     * Database properties.
     *
     * @param registry the registry
     */
//private static final GenericContainer memcached = new GenericContainer(DockerImageName.parse("memcached:1.6.23"));
    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        // Test container database
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.flyway.url", database::getJdbcUrl);
        registry.add("spring.flyway.schemas", database::getDatabaseName);
        registry.add("spring.flyway.defaultSchema", database::getDatabaseName);
        registry.add("spring.flyway.user", database::getUsername);
        registry.add("spring.flyway.password", database::getPassword);

        //Test container memcached
        /*registry.add("memcached.cache.servers",() -> String.format("%s:%d", memcached.getHost()
        , memcached.getExposedPorts().get(0)));
        registry.add("memcached.cache.expirations", ()->Integer.valueOf(3600));
        registry.add("memcached.cache.operation-timeout", ()->Integer.valueOf(3000));
        registry.add("memcached.cache.provider", ()->String.valueOf("static"));*/
    }

    @PostConstruct
    void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * Gets admin token.
     *
     * @return the admin token
     * @throws Exception the exception
     */
    String getAdminToken() throws Exception {
        if (!StringUtils.hasLength(token)) {
            MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/auth/authenticate")
                            .content("{\"username\":\"sheel.prabhakar@gmail.com\"," +
                                    " \"password\":\"admin123\", \"isOtp\":false}")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    //.andDo(print())
                    .andExpect(status().isOk()).andReturn();

            JwtResponse jwt = TestUtils
                    .convertJsonStringToObject(mvcResult.getResponse()
                            .getContentAsString(), JwtResponse.class);
            token = "Bearer " + jwt.getAccessToken();
        }
        return token;

    }

    MockHttpServletRequestBuilder post(final String baseUrl, Object resource) throws Exception {
        return MockMvcRequestBuilders
                .post(baseUrl)
                .content(TestUtils.convertObjectToJsonString(resource))
                .header("Authorization", getAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    MockHttpServletRequestBuilder put(final String baseUrl, Object resource) throws Exception {
        return MockMvcRequestBuilders
                .put(baseUrl)
                .content(TestUtils.convertObjectToJsonString(resource))
                .header("Authorization", getAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }
}
