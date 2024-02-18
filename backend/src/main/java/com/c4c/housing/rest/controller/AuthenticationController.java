package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.rest.resource.auth.JwtRequest;
import com.c4c.housing.rest.resource.auth.JwtResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Authentication controller.
 */
@Slf4j
@RestController()
@RequestMapping(AuthenticationController.BASE_URL)
public class AuthenticationController extends BaseController {
    /**
     * The Base url.
     */
    static final String BASE_URL = "/api/v1/auth";

    /**
     * Instantiates a new Authentication controller.
     *
     * @param restAdapterV1 the rest adapter v 1
     */
    @Autowired
    public AuthenticationController(final RestAdapterV1 restAdapterV1) {
        super(restAdapterV1);
    }

    /**
     * Do authentication and create authentication token response entity.
     *
     * @param authenticationRequest the authentication request
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(final @Valid @RequestBody
                                                        JwtRequest authenticationRequest) throws Exception {
        JwtResponse jwtResponse = this.getRestAdapterV1().authenticate(authenticationRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Logout response entity.
     *
     * @return the response entity
     * @throws Exception the exception
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout() throws Exception {
        this.getRestAdapterV1().logout();
        return ResponseEntity.ok("Logged out successfully");
    }

    /**
     * Refresh token response entity.
     *
     * @param refreshToken the token
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(final @RequestParam String refreshToken) throws Exception {
        JwtResponse jwtResponse = this.getRestAdapterV1().refreshToken(refreshToken);
        return ResponseEntity.ok(jwtResponse);
    }

}
