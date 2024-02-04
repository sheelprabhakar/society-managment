package com.c4c.housing;

import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type Housing society application tests.
 */
@SpringBootTest
class HousingSocietyApplicationTests {

    /**
     * The Password encoder.
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Context loads.
     */
    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("admin123"));

        for (UserEntity userEntity : userRepository.findAll()) {

            System.out.println(userEntity.getRoles().get(0));
        }

    }

}
