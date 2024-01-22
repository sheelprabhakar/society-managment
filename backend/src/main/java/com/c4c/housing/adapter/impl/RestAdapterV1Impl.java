package com.c4c.housing.adapter.impl;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.service.AuthenticationService;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.rest.resource.auth.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    @Autowired
    public RestAdapterV1Impl(final UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @Override
    public UserResource save(UserResource userResource) {
        return UserConverter.fromUserEntity( this.userService.save( UserConverter.fromUserResource(userResource)));
    }

    @Override
    public UserResource update(UserResource userResource) {
        return UserConverter.fromUserEntity(this.userService.update( UserConverter.fromUserResource(userResource)));
    }

    @Override
    public String authenticate(JwtRequest request) throws Exception {
        return this.authenticationService.authenticate(request.getUsername(), request.getPassword(), request.isOtp());
    }
}
