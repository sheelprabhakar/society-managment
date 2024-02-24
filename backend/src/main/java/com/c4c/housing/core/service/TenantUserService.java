package com.c4c.housing.core.service;

import com.c4c.housing.core.entity.TenantUserEntity;

import java.util.UUID;

/**
 * The interface Tenant user service.
 */
public interface TenantUserService {

    /**
     * Save tenant user entity.
     *
     * @param tenantId the tenant id
     * @param id       the id
     * @return the tenant user entity
     */
    TenantUserEntity save(UUID tenantId, UUID id);
}
