package com.c4c.housing.utils;

import com.c4c.housing.core.entity.Role;
import com.c4c.housing.core.entity.UserEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class UserEntityHelper {
    public static final String MOBILE = "9898989898";
    private UserEntityHelper(){}
    public static UserEntity getNew(UUID id){
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setEmail("ssp@c4c.com");
        entity.setIntro("");
        entity.setMobile(MOBILE);
        entity.setProfile("");
        entity.setLastLogin(null);

        entity.setLastName("prabhakar");
        entity.setMiddleName("s");
        entity.setPasswordHash("");
        entity.setFirstName("sheel");
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_ADMIN);
        entity.setRoles(roles);

        entity.setCreatedAt(Calendar.getInstance());
        entity.setUpdatedAt(Calendar.getInstance());
        entity.setCreatedBy("ssp");
        entity.setUpdatedBy("ssp_u");

        return entity;
    }
}
