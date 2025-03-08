package de.rjst.rjstbackendservice;

import org.springframework.boot.SpringApplication;

public class RjstBackendServiceTestApplication {

    public static void main(String[] args) {
        SpringApplication.from(RjstBackendServiceApplication::main)
                .with(TestcontainersConfiguration.class)
                .run(args);
    }

}
