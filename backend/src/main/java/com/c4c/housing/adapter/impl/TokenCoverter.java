package com.c4c.housing.adapter.impl;

import com.c4c.housing.core.entity.UserTokenEntity;
import com.c4c.housing.rest.resource.auth.JwtResponse;

public class TokenCoverter {
    private TokenCoverter(){

    }

    public static JwtResponse AuthSuccessInfo2JwtResponse(UserTokenEntity tokenEntity){
        return new JwtResponse(tokenEntity.getAccessToken(), tokenEntity.getRefreshToken());
    }
}
