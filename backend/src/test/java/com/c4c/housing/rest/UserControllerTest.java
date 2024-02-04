package com.c4c.housing.rest;

import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.utils.TestUtils;
import com.c4c.housing.utils.UserEntityHelper;
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
public class UserControllerTest extends AbstractIntegrationTest{
    public static final String MOBILE = "9898989898";
    private final String BASE_URL="/api/v1/user/";


    @Test
    public void test_add_user_ok() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL+"users")
                        .content(TestUtils.convertObjectToJsonString( UserEntityHelper.getNew(null)))
                        .header("Authorization", getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(MOBILE));
    }

    @Test
    public void test_update_user_ok() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "users")
                        .content(TestUtils.convertObjectToJsonString(UserEntityHelper.getNew(null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value(MOBILE)).andReturn();
        UserResource userResource = TestUtils
                .convertJsonStringToObject(mvcResult.getResponse()
                        .getContentAsString(), UserResource.class);
        userResource.setMobile("1234567890");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "users")
                        .content(TestUtils.convertObjectToJsonString(userResource))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").value("1234567890"));
    }
}
