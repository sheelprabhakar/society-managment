package com.c4c.housing.core.repository;

import com.c4c.housing.core.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    /**
     * Clear otp int.
     *
     * @param id the id
     * @return the int
     */
    @Modifying
    @Query("update user ue set ue.otp = NULL where ue.id = :id")
    int clearOTP(@Param("id") UUID id);

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    UserEntity findByEmail(String email);

    /**
     * Find by mobile user entity.
     *
     * @param mobile the mobile
     * @return the user entity
     */
    UserEntity findByMobile(String mobile);

    /**
     * Find by user name user entity.
     *
     * @param userName the user name
     * @return the user entity
     */
    UserEntity findByUserName(String userName);
}
