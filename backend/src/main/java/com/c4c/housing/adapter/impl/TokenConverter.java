package com.c4c.housing.adapter.impl;

import com.c4c.housing.core.entity.UserTokenEntity;
import com.c4c.housing.rest.resource.auth.JwtResponse;

/**
 * The type Token converter.
 */
public final class TokenConverter {
    /**
     * Instantiates a new Token converter.
     */
    private TokenConverter() {

    }

    /**
     * Auth success info to jwt response jwt response.
     *
     * @param tokenEntity the token entity
     * @return the jwt response
     */
    public static JwtResponse authSuccessInfoToJwtResponse(final UserTokenEntity tokenEntity) {
        return new JwtResponse(tokenEntity.getAccessToken(), tokenEntity.getRefreshToken());
    }
}
