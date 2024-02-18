package com.c4c.housing.rest;
import com.c4c.housing.rest.resource.TenantResource;
import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.utils.TenantResourceHelper;
import com.c4c.housing.utils.TestUtils;
import com.c4c.housing.utils.UserResourceHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TenantControllerTest extends AbstractIntegrationTest{
    private final String BASE_URL = "/api/v1/tenant";

    @Test
    public void test_create_tenant_ok() throws Exception {
        TenantResource resource = TenantResourceHelper.getNew();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(TestUtils.convertObjectToJsonString(resource))
                        .header("Authorization", getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));
    }
}
