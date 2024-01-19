package com.c4c.housing.rest;

import com.c4c.housing.HousingSocietyApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = HousingSocietyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class AbstractIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Container
    private static final MySQLContainer database = new MySQLContainer<>(DockerImageName.parse("mysql:8.1.0"));

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);


        registry.add("spring.flyway.url",database::getJdbcUrl);
        registry.add("spring.flyway.schemas",database::getDatabaseName);
        registry.add("spring.flyway.defaultSchema",database::getDatabaseName);
        registry.add("spring.flyway.user", database::getUsername);
        registry.add("spring.flyway.password", database::getPassword);
    }
}