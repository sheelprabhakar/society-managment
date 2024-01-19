package com.c4c.housing.core.service;

import com.c4c.housing.core.entity.UserEntity;

public interface UserService {
    UserEntity save(UserEntity userEntity);

    UserEntity findById(long id);

    UserEntity update(UserEntity userEntity);

    void clearOTP(UserEntity userEntity);

    boolean isOTPRequired(Long id);
}
