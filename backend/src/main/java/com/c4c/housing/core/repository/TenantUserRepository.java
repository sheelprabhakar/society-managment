package com.c4c.housing.core.repository;

import com.c4c.housing.core.entity.TenantUserEntity;
import com.c4c.housing.core.entity.TenantUserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantUserRepository extends JpaRepository<TenantUserEntity, TenantUserEntityId> {
}