package com.c4c.housing.core.service;

import com.c4c.housing.core.entity.TenantEntity;

/**
 * The interface Tenant service.
 */
public interface TenantService {
    /**
     * Create tenant entity.
     *
     * @param map the map
     * @return the tenant entity
     */
    TenantEntity create(TenantEntity map);
}
