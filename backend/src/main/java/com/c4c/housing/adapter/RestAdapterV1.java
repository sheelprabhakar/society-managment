package com.c4c.housing.adapter;

import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.rest.resource.UserResource;
import com.c4c.housing.rest.resource.auth.JwtRequest;

public interface RestAdapterV1 {
    UserResource save(UserResource userResource);

    UserResource update(UserResource userResource);

    String authenticate(JwtRequest request) throws Exception;
}
