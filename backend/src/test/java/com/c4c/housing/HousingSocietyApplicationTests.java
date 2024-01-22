package com.c4c.housing;

import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class HousingSocietyApplicationTests {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;
	@Test
	void contextLoads() {
		System.out.println(passwordEncoder.encode("admin123"));

		for (UserEntity userEntity : userRepository.findAll()) {

			System.out.println( userEntity.getRoles().get(0));
		}

	}

}
