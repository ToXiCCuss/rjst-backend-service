package de.rjst.rjstbackendservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EnvIT {

    static {
        System.setProperty("DOCKER_HOST", "tcp://vpn.rjst.de:2375");
    }

    @Test
    void contextLoads() {
        String getenv = System.getenv("DOCKER_HOST");
        System.out.println("DOCKER_HOST: " + getenv);
    }

}
