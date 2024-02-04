package com.c4c.housing.adapter;

import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.rest.resource.auth.JwtRequest;

/**
 * The interface Rest adapter v 1.
 */
public interface RestAdapterV1 {
    /**
     * Save user resource.
     *
     * @param userResource the user resource
     * @return the user resource
     */
    UserResource save(UserResource userResource);

    /**
     * Update user resource.
     *
     * @param userResource the user resource
     * @return the user resource
     */
    UserResource update(UserResource userResource);

    /**
     * Authenticate string.
     *
     * @param request the request
     * @return the string
     * @throws Exception the exception
     */
    String authenticate(JwtRequest request) throws Exception;
}
