package com.c4c.housing.rest.resource.auth;

import java.io.Serializable;

/**
 * The type Jwt response.
 */
public class JwtResponse implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -8091879091924046844L;
    /**
     * The Jwttoken.
     */
    private final String jwttoken;

    /**
     * Instantiates a new Jwt response.
     *
     * @param jwttoken the jwttoken
     */
    public JwtResponse(final String jwttoken) {
        this.jwttoken = jwttoken;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return this.jwttoken;
    }
}
