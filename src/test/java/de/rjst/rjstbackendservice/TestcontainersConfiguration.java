package de.rjst.rjstbackendservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static de.rjst.rjstbackendservice.container.ContainerImages.POSTGRESQL;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    private static final List<String> POSTGRESQL_PORTS = List.of("5432:5432");

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

}
