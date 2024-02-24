package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.common.SpringUtil;

import java.util.UUID;

/**
 * The type Base controller.
 */
public abstract class BaseController {
    /**
     * The Rest adapter v 1.
     */
    private final RestAdapterV1 restAdapterV1;

    /**
     * Instantiates a new Base controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    public BaseController(final RestAdapterV1 restAdapterV1) {
        this.restAdapterV1 = restAdapterV1;
    }

    /**
     * Is super admin boolean.
     *
     * @return the boolean
     */
    boolean isSuperAdmin() {
        return SpringUtil.isSuperAdmin();
    }

    /**
     * Is tenant admin boolean.
     *
     * @return the boolean
     */
    boolean isTenantAdmin() {
        return SpringUtil.isTenantAdmin();
    }

    /**
     * Gets tenant id.
     *
     * @return the tenant id
     */
    UUID getTenantId() {
        return SpringUtil.getTenantId();
    }

    /**
     * Gets rest adapter v 1.
     *
     * @return the rest adapter v 1
     */
    public RestAdapterV1 getRestAdapterV1() {
        return restAdapterV1;
    }

}
