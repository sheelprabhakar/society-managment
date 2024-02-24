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
        this.mockMvc.perform(this.post(BASE_URL, resource))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(resource.getName()));
    }

    @Test
    public void test_create_tenant_duplicate() throws Exception {
        TenantResource resource = TenantResourceHelper.getNew();
        String result = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TenantResource tenantResource = TestUtils.convertJsonStringToObject(result, TenantResource.class);
        resource = TenantResourceHelper.getNew();
        resource.setName(tenantResource.getName());

        this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void test_create_tenant_validation() throws Exception {
        TenantResource resource = TenantResourceHelper.getNew();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(TestUtils.convertObjectToJsonString(resource))
                        .header("Authorization", "sdfsdffhsgj")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isUnauthorized());

        resource.setCityId(-1);
        resource.setEmail(null);
        this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_create_tenant_update_ok() throws Exception {
        TenantResource resource = TenantResourceHelper.getNew();
        String result = this.mockMvc.perform(this.post(BASE_URL, resource))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        TenantResource tenantResource = TestUtils.convertJsonStringToObject(result, TenantResource.class);
        tenantResource.setName(tenantResource.getName()+"dup");

        this.mockMvc.perform(this.put(BASE_URL, tenantResource))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(tenantResource.getName()));

        tenantResource.setName(null);

        this.mockMvc.perform(this.put(BASE_URL, tenantResource))
                //.andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
    }
}
