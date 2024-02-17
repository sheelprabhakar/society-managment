package com.c4c.housing.adapter.impl;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.service.AuthenticationService;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.rest.resource.auth.JwtRequest;
import com.c4c.housing.rest.resource.auth.JwtResponse;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper exactNameModelMapper;
    @Autowired
    public RestAdapterV1Impl(final UserService userService,
                             final AuthenticationService authenticationService, ModelMapper exactNameModelMapper) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.exactNameModelMapper = exactNameModelMapper;
    }

    /**
     * Save user resource.
     *
     * @param userResource the user resource
     * @return the user resource
     */
    @Override
    public UserResource save(final UserResource userResource) {
        return this.exactNameModelMapper.map(this.userService.save(
                this.exactNameModelMapper.map(userResource, UserEntity.class)),
                UserResource.class);
    }

    /**
     * Update user resource.
     *
     * @param userResource the user resource
     * @return the user resource
     */
    @Override
    public UserResource update(final UserResource userResource) {
        return this.exactNameModelMapper.map(this.userService.update(
                this.exactNameModelMapper.map(userResource, UserEntity.class)),
                UserResource.class);
    }

    /**
     * Authenticate string.
     *
     * @param request the request
     * @return the string
     * @throws Exception the exception
     */
    @Override
    public JwtResponse authenticate(final JwtRequest request) throws Exception {
        return this.exactNameModelMapper.map(
                this.authenticationService.authenticate(request.getUsername(),
                request.getPassword(), request.isOtp()), JwtResponse.class);
    }

    /**
     * Logout.
     */
    @Override
    public void logout() {
        this.authenticationService.logout();
    }

    /**
     * Refresh token jwt response.
     *
     * @param refreshToken the token
     * @return the jwt response
     */
    @Override
    public JwtResponse refreshToken(final String refreshToken) {
        return this.exactNameModelMapper.map(
                this.authenticationService.refreshToken(refreshToken), JwtResponse.class);
    }
}
