package com.c4c.housing.core.service;

import com.c4c.housing.core.entity.UserTokenEntity;

import java.util.UUID;

public interface UserTokenService {
    UserTokenEntity getById(UUID id);

    UserTokenEntity update(UUID id, String token);
}
