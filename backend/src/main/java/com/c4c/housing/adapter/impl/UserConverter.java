package com.c4c.housing.adapter.impl;

import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.rest.resource.UserResource;

public class UserConverter {
    private UserConverter(){

    }
    public static UserResource fromUserEntity(final UserEntity entity){
        UserResource resource = new UserResource();
        resource.setId(entity.getId());
        resource.setEmail(entity.getEmail());
        resource.setIntro(entity.getIntro());
        resource.setMobile(entity.getMobile());
        resource.setProfile(entity.getProfile());
        resource.setLastLogin(entity.getLastLogin());

        resource.setLastName(entity.getLastName());
        resource.setMiddleName(entity.getMiddleName());
        resource.setPasswordHash("");
        resource.setFirstName(entity.getFirstName());

        resource.setCreatedAt(entity.getCreatedAt());
        resource.setUpdatedAt(entity.getUpdatedAt());
        resource.setCreatedBy(entity.getCreatedBy());
        resource.setUpdatedBy(entity.getUpdatedBy());
        return  resource;
    }
    public static UserEntity fromUserResource(final UserResource resource){
        UserEntity entity = new UserEntity();
        entity.setId(resource.getId());
        entity.setEmail(resource.getEmail());
        entity.setIntro(resource.getIntro());
        entity.setMobile(resource.getMobile());
        entity.setProfile(resource.getProfile());
        entity.setLastLogin(resource.getLastLogin());

        entity.setLastName(resource.getLastName());
        entity.setMiddleName(resource.getMiddleName());
        entity.setPasswordHash("");
        entity.setFirstName(resource.getFirstName());

        entity.setCreatedAt(resource.getCreatedAt());
        entity.setUpdatedAt(resource.getUpdatedAt());
        entity.setCreatedBy(resource.getCreatedBy());
        entity.setUpdatedBy(resource.getUpdatedBy());
        return entity;
    }
}