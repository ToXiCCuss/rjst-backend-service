package de.rjst.bes.container;

import static de.rjst.bes.container.ContainerImages.MOCK_SERVER;
import static de.rjst.bes.container.ContainerImages.POSTGRESQL;
import static de.rjst.bes.container.ContainerImages.REDIS;

import com.redis.testcontainers.RedisContainer;
import org.mockserver.client.MockServerClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class JUnitContainersConfiguration {


    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        final var container = new PostgreSQLContainer<>(POSTGRESQL);
        container.withUsername("app_backend");
        container.withPassword("password");
        container.withDatabaseName("backend");
        return container;
    }

    @Bean
    @ServiceConnection(name = "redis")
    public RedisContainer redisContainer() {
        return new RedisContainer(REDIS);
    }

    @Bean
    public MockServerContainer mockServerContainer() {
        return new MockServerContainer(MOCK_SERVER);
    }

    @Bean
    public MockServerClient mockServerClient(final MockServerContainer mockServerContainer) {
        mockServerContainer.start();
        return new MockServerClient(mockServerContainer.getHost(), mockServerContainer.getServerPort());
    }

    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(final MockServerContainer mockServerContainer) {
        return registry -> {
            registry.add("spring.cloud.openfeign.client.config.ipQuery.url", mockServerContainer::getEndpoint);
        };
    }

}
