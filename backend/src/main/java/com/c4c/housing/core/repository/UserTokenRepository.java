package com.c4c.housing.core.repository;

import com.c4c.housing.core.entity.UserTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserTokenRepository extends CrudRepository<UserTokenEntity, UUID> {
}
