package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.rest.resource.TenantResource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    /**
     * Create response entity.
     *
     * @param tenantResource the tenant resource
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<TenantResource> create(@Valid @RequestBody final TenantResource tenantResource) {
        TenantResource resource = this.getRestAdapterV1().createTenant(tenantResource);
        return ResponseEntity.created(URI.create(BASE_URL + "/" + resource.getId()))
                .body(resource);
    }

    /**
     * Update response entity.
     *
     * @param tenantResource the tenant resource
     * @return the response entity
     */
    @PutMapping
    public ResponseEntity<TenantResource> update(@Valid @RequestBody final TenantResource tenantResource) {
        TenantResource resource = this.getRestAdapterV1().updateTenant(tenantResource);
        return ResponseEntity.ok()
                .body(resource);
    }

    /**
     * Read response entity.
     *
     * @param tenantId the tenant id
     * @return the response entity
     */
    @GetMapping("/{tenantId}")
    public ResponseEntity<?> read(@PathVariable("tenantId") final UUID tenantId) {
        if (this.isSuperAdmin() || tenantId.equals(this.getTenantId())) {
            TenantResource resource = this.getRestAdapterV1().readTenant(tenantId);
            return ResponseEntity.ok()
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can not access this resource.");
        }
    }

    /**
     * Read response entity.
     *
     * @return the response entity
     */
    @GetMapping()
    public ResponseEntity<List<TenantResource>> read() {
        if (this.isSuperAdmin()) {
            List<TenantResource> resourceList = this.getRestAdapterV1().readTenants();
            return ResponseEntity.ok()
                    .body(resourceList);
        } else {
            TenantResource resource = this.getRestAdapterV1().readTenant(this.getTenantId());
            return ResponseEntity.ok()
                    .body(Arrays.asList(resource));
        }
    }
}
