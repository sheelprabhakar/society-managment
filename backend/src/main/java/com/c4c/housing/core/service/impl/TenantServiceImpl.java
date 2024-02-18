package com.c4c.housing.core.service.impl;


import com.c4c.housing.core.entity.TenantEntity;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.TenantRepository;
import com.c4c.housing.core.service.TenantService;
import com.c4c.housing.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * The type Tenant service.
 */
@Service
@Slf4j
@Transactional
public class TenantServiceImpl implements TenantService {
    /**
     * The Tenant repository.
     */
    private final TenantRepository tenantRepository;

    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * Instantiates a new Tenant service.
     *
     * @param tenantRepository the tenant repository
     * @param userService      the user service
     */
    public TenantServiceImpl(final TenantRepository tenantRepository,
                             final UserService userService) {
        this.tenantRepository = tenantRepository;
        this.userService = userService;
    }


    /**
     * Create tenant entity.
     *
     * @param tenantEntity the map
     * @return the tenant entity
     */
    @Override
    public TenantEntity create(final TenantEntity tenantEntity) {
        tenantEntity.setActive(true);
        TenantEntity entity = this.tenantRepository.save(tenantEntity);
        // If User not register then automatically register admin user
        if (Objects.isNull(this.userService.findByEmail(tenantEntity.getEmail()))) {
            UserEntity userEntity = getNewUserEntity(tenantEntity);
            this.userService.save(userEntity);
        }
        return entity;
    }

    /**
     * Gets new user entity.
     *
     * @param tenantEntity the tenant entity
     * @return the new user entity
     */
    private static UserEntity getNewUserEntity(final TenantEntity tenantEntity) {

        return UserEntity.builder().userName(tenantEntity.getShortName())
                .email(tenantEntity.getEmail())
                .mobile(tenantEntity.getMobile())
                .firstName(tenantEntity.getShortName())
                .lastName(tenantEntity.getShortName())
                .passwordHash("admin123").build();
    }
}
