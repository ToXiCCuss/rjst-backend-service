package de.rjst.bes;

import de.rjst.bes.container.config.LocalContainersConfiguration;
import org.springframework.boot.SpringApplication;

public class RjstBackendServiceTestApplication {

    public static void main(String[] args) {
        SpringApplication.from(RjstBackendServiceApplication::main)
                .with(LocalContainersConfiguration.class)
                .withAdditionalProfiles("dev")
                .run(args);
    }

}
