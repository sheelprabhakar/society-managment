package com.c4c.housing.rest.resource.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * The type Jwt response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -8091879091924046844L;

    /**
     * The Access token.
     */
    private String accessToken;

    /**
     * The Refresh token.
     */
    private String refreshToken;

}
