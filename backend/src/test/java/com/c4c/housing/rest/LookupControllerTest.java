package com.c4c.housing.rest;
import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.utils.TestUtils;
import com.c4c.housing.utils.UserResourceHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LookupControllerTest extends AbstractIntegrationTest {
    private final String BASE_URL = "/api/v1/lookup";

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
