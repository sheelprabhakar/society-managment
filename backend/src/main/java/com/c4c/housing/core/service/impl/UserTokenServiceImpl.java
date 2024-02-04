package com.c4c.housing.core.service.impl;

import com.c4c.housing.core.entity.UserTokenEntity;
import com.c4c.housing.core.repository.UserTokenRepository;
import com.c4c.housing.core.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

/**
 * The type User token service.
 */
@Service
public class UserTokenServiceImpl implements UserTokenService {
    /**
     * The User token repository.
     */
    private final UserTokenRepository userTokenRepository;

    /**
     * Instantiates a new User token service.
     *
     * @param userTokenRepository the user token repository
     */
    @Autowired
    public UserTokenServiceImpl(final UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    @Override
    @Cacheable(value = "tokens", key = "#id.toString()")
    public UserTokenEntity getById(final UUID id) {
        return this.userTokenRepository.findById(id).orElse(null);
    }

    /**
     * Update user token entity.
     *
     * @param id    the id
     * @param token the token
     * @return the user token entity
     */
    @Override
    @CacheEvict(value = "tokens", key = "#id.toString()")
    public UserTokenEntity update(final UUID id, final String token) {
        UserTokenEntity tokenEntity = this.userTokenRepository.findById(id).orElse(null);
        if (tokenEntity == null) {
            tokenEntity = new UserTokenEntity();
            tokenEntity.setId(id);
        }

        tokenEntity.setToken(token);
        tokenEntity.setUpdatedAt(Calendar.getInstance());

        return this.userTokenRepository.save(tokenEntity);
    }

}
