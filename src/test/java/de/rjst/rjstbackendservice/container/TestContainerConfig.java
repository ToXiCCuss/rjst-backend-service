package de.rjst.rjstbackendservice.container;

import org.mockserver.client.MockServerClient;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static de.rjst.rjstbackendservice.container.ContainerImages.MOCK_SERVER;
import static de.rjst.rjstbackendservice.container.ContainerImages.POSTGRESQL;

public interface TestContainerConfig {

    @Container
    @ServiceConnection
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(POSTGRESQL);

    @Container
    MockServerContainer mockServer = new MockServerContainer(MOCK_SERVER);

    static MockServerClient getClient() {
        return new MockServerClient(
                mockServer.getHost(),
                mockServer.getServerPort()
        );
    }
}
