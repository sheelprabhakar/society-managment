package com.c4c.housing.adapter.impl;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.core.service.AuthenticationService;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.rest.resource.auth.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Rest adapter v 1.
 */
@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
    /**
     * The User service.
     */
    private final UserService userService;
    /**
     * The Authentication service.
     */
    private final AuthenticationService authenticationService;

    /**
     * Instantiates a new Rest adapter v 1.
     *
     * @param userService           the user service
     * @param authenticationService the authentication service
     */
    @Autowired
    public RestAdapterV1Impl(final UserService userService, final AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    /**
     * Save user resource.
     *
     * @param userResource the user resource
     * @return the user resource
     */
    @Override
    public UserResource save(final UserResource userResource) {
        return UserConverter.fromUserEntity(this.userService.save(UserConverter.fromUserResource(userResource)));
    }

    /**
     * Update user resource.
     *
     * @param userResource the user resource
     * @return the user resource
     */
    @Override
    public UserResource update(final UserResource userResource) {
        return UserConverter.fromUserEntity(this.userService.update(UserConverter.fromUserResource(userResource)));
    }

    /**
     * Authenticate string.
     *
     * @param request the request
     * @return the string
     * @throws Exception the exception
     */
    @Override
    public String authenticate(final JwtRequest request) throws Exception {
        return this.authenticationService.authenticate(request.getUsername(), request.getPassword(), request.isOtp());
    }
}
