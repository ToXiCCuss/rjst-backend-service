package de.rjst.bes.container;

import static de.rjst.bes.container.ContainerImages.MOCK_SERVER;
import static de.rjst.bes.container.ContainerImages.POSTGRESQL;

import lombok.Getter;
import org.mockserver.client.MockServerClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class JUnitContainersConfiguration {

    @Getter
    private static MockServerClient mockServerClient;

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
    public MockServerContainer mockServerContainer() {
        final var mockServerContainer = new MockServerContainer(MOCK_SERVER);
        mockServerContainer.start();
        mockServerClient = new MockServerClient(
            mockServerContainer.getHost(),
            mockServerContainer.getServerPort()
        );
        return mockServerContainer;
    }

    @Bean
    public DynamicPropertyRegistrar dynamicPropertyRegistrar(final MockServerContainer mockServerContainer) {
        return registry -> {
            registry.add("spring.cloud.openfeign.client.config.finance.url", mockServerContainer::getEndpoint);
            registry.add("spring.cloud.openfeign.client.config.ipQuery.url", mockServerContainer::getEndpoint);
        };
    }

}
