package de.rjst.bes.security;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityMethodeConfig {
}
