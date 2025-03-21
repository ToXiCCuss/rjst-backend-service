package de.rjst.bes.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.Delimiter;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Configuration
@ConfigurationProperties("security")
public class SecurityProperties {

    @Delimiter(",")
    private String[] permitAll = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator",
            "/actuator/**",
            "/error",
            "/test",
            "/",
            "/swagger-ui.html",
            "/public/**",
    };

    private Boolean enabled = true;

}
