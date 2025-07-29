package de.rjst.bes.container.config;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static de.rjst.bes.container.config.ContainerImages.POSTGRESQL;
import static de.rjst.bes.container.config.ContainerImages.REDIS;

@TestConfiguration(proxyBeanMethods = false)
public class LocalContainersConfiguration {

    private static final List<String> POSTGRESQL_PORTS = List.of("5432:5432");
    private static final List<String> REDIS_PORTS = List.of("6379:6379");

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        final var container = new PostgreSQLContainer<>(POSTGRESQL);
        container.withUsername("app_backend");
        container.withPassword("password");
        container.withDatabaseName("backend");
        container.setPortBindings(POSTGRESQL_PORTS);
        return container;
    }

    @Bean
    @ServiceConnection(name = "redis")
    public RedisContainer redisContainer() {
        final var redisContainer = new RedisContainer(REDIS);
        redisContainer.setPortBindings(REDIS_PORTS);
        return redisContainer;
    }

}
