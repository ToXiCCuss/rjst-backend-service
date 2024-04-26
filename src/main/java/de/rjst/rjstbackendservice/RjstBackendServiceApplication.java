package de.rjst.rjstbackendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;


@EnableFeignClients
@SpringBootApplication
public class RjstBackendServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RjstBackendServiceApplication.class, args);
    }

}
