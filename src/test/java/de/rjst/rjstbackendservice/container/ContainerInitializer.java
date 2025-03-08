package de.rjst.rjstbackendservice.container;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import static de.rjst.rjstbackendservice.container.TestContainerConfig.mockServer;

@ImportTestcontainers(TestContainerConfig.class)
public class ContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        mockServer.start();
        String endpoint = mockServer.getEndpoint();
        TestPropertyValues.of(
                "spring.cloud.openfeign.client.config.ipQuery.url=" + endpoint
        ).applyTo(applicationContext);
    }

}
