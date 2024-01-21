package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.rest.resource.auth.JwtRequest;
import com.c4c.housing.rest.resource.auth.JwtResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(AuthenticationController.BASE_URL)
public class AuthenticationController {
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);
    static final String BASE_URL = "/api/v1/auth";
    private final RestAdapterV1 restAdapterV1;

    @Autowired
    public AuthenticationController(RestAdapterV1 restAdapterV1) {
        this.restAdapterV1 = restAdapterV1;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String token = this.restAdapterV1.authenticate(authenticationRequest);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
