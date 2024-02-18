package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
    /**
     * The Rest adapter v 1.
     */
    final RestAdapterV1 restAdapterV1;

    public BaseController(final RestAdapterV1 restAdapterV1) {
        this.restAdapterV1 = restAdapterV1;
    }
}
