package com.c4c.housing.core.repository;

import com.c4c.housing.core.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    @Modifying
    @Query("update user ue set ue.otp = NULL where ue.id = :id")
    int clearOTP(@Param("id") Long id);

    UserEntity findByEmail(String email);

    UserEntity findByMobile(String mobile);

    UserEntity findByUserName(String userName);
}
