package com.c4c.housing.utils;

import com.c4c.housing.rest.resource.UserResource;

import java.util.Calendar;
import java.util.UUID;

/**
 * The type User resource helper.
 */
public class UserResourceHelper {
    /**
     * The constant MOBILE.
     */
    public static final String MOBILE = "989898989";

    /**
     * Instantiates a new User resource helper.
     */
    private UserResourceHelper() {
    }

    /**
     * The Counter.
     */
    static int counter = 0;

    /**
     * Gets new.
     *
     * @param id the id
     * @return the new
     */
    public static UserResource getNew(final UUID id) {
        int c = counter++;
        UserResource resource = new UserResource();
        resource.setId(id);
        resource.setEmail(String.format("ssp%d@c4c.com", c));
        resource.setIntro("");
        resource.setMobile(MOBILE + c);
        resource.setProfile("");
        resource.setLastLogin(null);
        resource.setUserName("username"+c);
        resource.setLastName("prabhakar");
        resource.setMiddleName("s");
        resource.setPasswordHash("");
        resource.setFirstName("sheel");

        resource.setCreatedAt(Calendar.getInstance());
        resource.setUpdatedAt(Calendar.getInstance());
        resource.setCreatedBy("ssp");
        resource.setUpdatedBy("ssp_u");

        return resource;
    }
}
