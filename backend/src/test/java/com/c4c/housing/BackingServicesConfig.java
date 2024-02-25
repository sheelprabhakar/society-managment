package com.c4c.housing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * The type Backing services config.
 */
@SpringBootTest
@Testcontainers
public abstract class BackingServicesConfig {

    /**
     * The constant database.
     */
    @Container
    public static final MySQLContainer database = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.1.0"));

    @Container
    public static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379);

    /**
     * Database properties.
     *
     * @param registry the registry
     */
    @DynamicPropertySource
    public static void databaseProperties(DynamicPropertyRegistry registry) {
        // Test container database
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.flyway.url", database::getJdbcUrl);
        registry.add("spring.flyway.schemas", database::getDatabaseName);
        registry.add("spring.flyway.defaultSchema", database::getDatabaseName);
        registry.add("spring.flyway.user", database::getUsername);
        registry.add("spring.flyway.password", database::getPassword);

        //Test container memcached
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);
    }

    @AfterAll
    public static void stopServer() {

    }

    @BeforeAll
    public static void startServer() {

    }
}
