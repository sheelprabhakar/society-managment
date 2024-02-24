package com.c4c.housing.core.service.impl;


import com.c4c.housing.core.entity.TenantEntity;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.TenantRepository;
import com.c4c.housing.core.service.TenantService;
import com.c4c.housing.core.service.TenantUserService;
import com.c4c.housing.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
     * The Tenant user service.
     */
    private final TenantUserService tenantUserService;

    /**
     * Instantiates a new Tenant service.
     *
     * @param tenantRepository  the tenant repository
     * @param userService       the user service
     * @param tenantUserService the tenant user service
     */
    public TenantServiceImpl(final TenantRepository tenantRepository,
                             final UserService userService, final TenantUserService tenantUserService) {
        this.tenantRepository = tenantRepository;
        this.userService = userService;
        this.tenantUserService = tenantUserService;
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
        return this.saveTenantEntity(tenantEntity);
    }

    /**
     * Save tenant entity tenant entity.
     *
     * @param tenantEntity the tenant entity
     * @return the tenant entity
     */
    private TenantEntity saveTenantEntity(final TenantEntity tenantEntity) {
        TenantEntity entity = this.tenantRepository.save(tenantEntity);
        // If User not register then automatically register admin user
        if (Objects.isNull(this.userService.findByEmail(entity.getEmail()))) {
            UserEntity userEntity = getNewUserEntity(entity);
            userEntity = this.userService.save(userEntity);
            // Todo: implement mapping of user to tenant
            this.tenantUserService.save(entity.getId(), userEntity.getId());
        }
        return entity;
    }

    /**
     * Update tenant entity.
     *
     * @param tenantEntity the tenant entity
     * @return the tenant entity
     */
    @Override
    public TenantEntity update(final TenantEntity tenantEntity) {
        return this.saveTenantEntity(tenantEntity);
    }

    /**
     * Read tenant entity.
     *
     * @param tenantId the tenant id
     * @return the tenant entity
     */
    @Override
    public TenantEntity read(final UUID tenantId) {
        return this.tenantRepository.findById(tenantId).get();
    }

    /**
     * Read all list.
     *
     * @return the list
     */
    @Override
    public List<TenantEntity> readAll() {
        return this.tenantRepository.findAll();
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
