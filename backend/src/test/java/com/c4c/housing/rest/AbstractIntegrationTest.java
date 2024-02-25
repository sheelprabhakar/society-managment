package com.c4c.housing.rest;

import com.c4c.housing.BackingServicesConfig;
import com.c4c.housing.HousingSocietyApplication;
import com.c4c.housing.config.security.JwtTokenProvider;
import com.c4c.housing.rest.resource.auth.JwtResponse;
import com.c4c.housing.utils.TestUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Abstract integration test.
 */
@SpringBootTest(classes = HousingSocietyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractIntegrationTest extends BackingServicesConfig {
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
     * Init.
     */
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

    /**
     * Post mock http servlet request builder.
     *
     * @param baseUrl  the base url
     * @param resource the resource
     * @return the mock http servlet request builder
     * @throws Exception the exception
     */
    MockHttpServletRequestBuilder post(final String baseUrl, Object resource) throws Exception {
        return MockMvcRequestBuilders
                .post(baseUrl)
                .content(TestUtils.convertObjectToJsonString(resource))
                .header("Authorization", getAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    MockHttpServletRequestBuilder get(final String url) throws Exception {
        return MockMvcRequestBuilders
                .get(url)
                .header("Authorization", getAdminToken())
                .accept(MediaType.APPLICATION_JSON);
    }

    /**
     * Put mock http servlet request builder.
     *
     * @param baseUrl  the base url
     * @param resource the resource
     * @return the mock http servlet request builder
     * @throws Exception the exception
     */
    MockHttpServletRequestBuilder put(final String baseUrl, Object resource) throws Exception {
        return MockMvcRequestBuilders
                .put(baseUrl)
                .content(TestUtils.convertObjectToJsonString(resource))
                .header("Authorization", getAdminToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }
}
