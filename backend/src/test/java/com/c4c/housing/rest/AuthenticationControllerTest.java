package com.c4c.housing.rest;

import com.c4c.housing.rest.resource.auth.JwtResponse;
import com.c4c.housing.utils.TestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * The type Authentication controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthenticationControllerTest extends AbstractIntegrationTest {
    /**
     * The constant MOBILE.
     */
    public static final String MOBILE = "9898989898";
    /**
     * The Base url.
     */
    private final String BASE_URL = "/api/v1/auth";

    /**
     * The Unq.
     */
    private final int unq = 0;

    /**
     * Test authenticate ok.
     *
     * @throws Exception the exception
     */
    @Test
    public void test_authenticate_ok() throws Exception {
        assertTrue(this.getAdminToken().startsWith("Bearer"));

        String response= this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{\"username\":\"sheel.prabhakar@gmail.com\"," +
                                " \"password\":\"admin123\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JwtResponse jwtResponse = TestUtils.convertJsonStringToObject(response, JwtResponse.class);
        assertNotNull(jwtResponse.getAccessToken());
        Claims payload = Jwts.parser().setSigningKey(secretKey).build()
                .parseClaimsJws(jwtResponse.getAccessToken()).getPayload();
        assertEquals(payload.getSubject(), "sheel.prabhakar@gmail.com");
        List<Map<String, String>> authorities = (List<Map<String, String>>) payload.get("authorities");
        assertEquals(authorities.size(), 2);
        assertNotNull(jwtResponse.getRefreshToken());
    }

    /**
     * Test authenticate wrong credentials.
     *
     * @throws Exception the exception
     */
    @Test
    public void test_authenticate_wrong_credentials() throws Exception {
        String msg = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{\"username\":\"sheel.prabhakar@gmail.com\"," +
                                " \"password\":\"admin1234\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertEquals("Invalid credentials", msg);

        msg = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{\"username\":\"sheel.prabhakar123@gmail.com\"," +
                                " \"password\":\"admin123\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
        assertEquals("Invalid credentials", msg);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/authenticate")
                        .content("{ \"password\":\"admin123\", \"isOtp\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.detail").value("Invalid request content."));

    }

    @Test
    public void test_logout_ok() throws Exception {
        String token = getAdminToken();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/logout")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/logout")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
