package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.rest.resource.lookup.CityResource;
import com.c4c.housing.rest.resource.lookup.CountryResource;
import com.c4c.housing.rest.resource.lookup.StateResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Lookup controller.
 */
@Slf4j
@RestController()
@RequestMapping(LookupController.BASE_URL)
public class LookupController {
    /**
     * The Base url.
     */
    static final String BASE_URL = "/api/v1/lookup";
    /**
     * The Rest adapter v 1.
     */
    private final RestAdapterV1 restAdapterV1;

    /**
     * Instantiates a new Lookup controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    public LookupController(final RestAdapterV1 restAdapterV1) {
        this.restAdapterV1 = restAdapterV1;
    }

    /**
     * Countries response entity.
     *
     * @return the response entity
     * @throws Exception the exception
     */
    @GetMapping("/country")
    public ResponseEntity<List<CountryResource>> countries() throws Exception {
        List<CountryResource> countryResources = this.restAdapterV1.countries();
        return ResponseEntity.ok(countryResources);
    }

    /**
     * States response entity.
     *
     * @param countryId the country id
     * @return the response entity
     * @throws Exception the exception
     */
    @GetMapping("/country/{countryId}/state")
    public ResponseEntity<List<StateResource>> states(@PathVariable("countryId") int countryId) throws Exception {
        List<StateResource> stateResources = this.restAdapterV1.states(countryId);
        return ResponseEntity.ok(stateResources);
    }

    @GetMapping("/state/{stateId}/city")
    public ResponseEntity<List<CityResource>> cities(@PathVariable("stateId") int stateId) throws Exception {
        List<CityResource> cityResources = this.restAdapterV1.cities(stateId);
        return ResponseEntity.ok(cityResources);
    }
}
