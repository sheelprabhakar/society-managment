package com.c4c.housing.adapter.impl;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestAdapterV1Impl implements RestAdapterV1 {
    private final UserService userService;

    @Autowired
    public RestAdapterV1Impl(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResource save(UserResource userResource) {
        return UserConverter.fromUserEntity( this.userService.save( UserConverter.fromUserResource(userResource)));
    }

    @Override
    public UserResource update(UserResource userResource) {
        return UserConverter.fromUserEntity(this.userService.update( UserConverter.fromUserResource(userResource)));
    }
}
