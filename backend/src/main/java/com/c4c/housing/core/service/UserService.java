package com.c4c.housing.core.service;

import com.c4c.housing.core.entity.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity save(UserEntity userEntity);

    UserEntity findById(UUID id);

    UserEntity findByEmail(String email);

    UserEntity update(UserEntity userEntity);

    void clearOTP(UserEntity userEntity);

    boolean isOTPRequired(UUID id);

    boolean isOTPRequired(UserEntity userEntity);
}
