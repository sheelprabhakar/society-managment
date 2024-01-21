package com.c4c.housing.core.entity;

import java.io.Serializable;
import java.util.UUID;

public class UserRoleId implements Serializable {
    private UUID roleId;
    private UUID userId;

    public UserRoleId(UUID roleId, UUID userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public UserRoleId() {

    }
}
