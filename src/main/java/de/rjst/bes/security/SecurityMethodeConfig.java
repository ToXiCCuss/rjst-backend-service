package de.rjst.bes.security;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
@EnableMethodSecurity
public class SecurityMethodeConfig {
}
