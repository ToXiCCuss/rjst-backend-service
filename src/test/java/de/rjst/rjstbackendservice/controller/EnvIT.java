package de.rjst.rjstbackendservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnvIT {

    @Test
    void contextLoads() {
        String getenv = System.getenv("DOCKER_HOST");
        System.out.println("DOCKER_HOST: " + getenv);
    }

}
