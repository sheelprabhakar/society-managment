package com.c4c.housing.core.repository;

import com.c4c.housing.core.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, UUID> {
}
