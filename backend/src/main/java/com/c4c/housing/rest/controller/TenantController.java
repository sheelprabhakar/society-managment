package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.rest.resource.TenantResource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * The type Tenant controller.
 */
@Slf4j
@RestController()
@RequestMapping(TenantController.BASE_URL)
public class TenantController extends BaseController {
    /**
     * The Base url.
     */
    static final String BASE_URL = "/api/v1/tenant";

    /**
     * Instantiates a new Tenant controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    public TenantController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    @PostMapping
    public ResponseEntity<TenantResource> create(@Valid @RequestBody TenantResource tenantResource){
        TenantResource resource = this.restAdapterV1.createTenant(tenantResource);
        return ResponseEntity.created(URI.create(BASE_URL + "/"+ resource.getId()))
                .body(resource);
    }
}
