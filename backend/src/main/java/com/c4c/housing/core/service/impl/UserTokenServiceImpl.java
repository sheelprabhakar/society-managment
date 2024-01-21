package com.c4c.housing.core.service.impl;

import com.c4c.housing.core.entity.UserTokenEntity;
import com.c4c.housing.core.repository.UserTokenRepository;
import com.c4c.housing.core.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class UserTokenServiceImpl implements UserTokenService {
    private final UserTokenRepository userTokenRepository;

    @Autowired
    public UserTokenServiceImpl(final UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @Override
    public UserTokenEntity getById(UUID id){
        return this.userTokenRepository.findById(id).orElse(null);
    }

    @Override
    public UserTokenEntity update(UUID id, String token){
        UserTokenEntity tokenEntity = this.userTokenRepository.findById(id).orElse(null);
        if(tokenEntity == null){
            tokenEntity = new UserTokenEntity();
            tokenEntity.setId(id);
        }

        tokenEntity.setToken(token);
        tokenEntity.setUpdatedAt(Calendar.getInstance());

        return this.userTokenRepository.save(tokenEntity);
    }

}
