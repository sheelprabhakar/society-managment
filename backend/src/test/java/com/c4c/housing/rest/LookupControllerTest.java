package com.c4c.housing.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Lookup controller test.
 */
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LookupControllerTest extends AbstractIntegrationTest {
    /**
     * The Base url.
     */
    private final String BASE_URL = "/api/v1/lookup";

    /**
     * Test get countries ok.
     *
     * @throws Exception the exception
     */
    @Test
    public void test_get_countries_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/country")
                        .header("Authorization", getAdminToken())
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(250));
    }

    /**
     * Test get states ok.
     *
     * @throws Exception the exception
     */
    @Test
    public void test_get_states_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/country/101/state")
                        .header("Authorization", getAdminToken())
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(37));
    }

    /**
     * Test get cities ok.
     *
     * @throws Exception the exception
     */
    @Test
    public void test_get_cities_ok() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL+"/state/4022/city")
                        .header("Authorization", getAdminToken())
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(499));
    }
}
