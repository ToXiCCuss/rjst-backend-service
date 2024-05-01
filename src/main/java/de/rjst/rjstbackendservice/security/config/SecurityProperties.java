package de.rjst.rjstbackendservice.security.config;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String pseudoUserPath;

    @NotBlank
    private String defaultUserPath;

    @NotBlank
    private String toolUser;

    @Delimiter(",")
    private String[] permitAll = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/public/**",
            "/actuator/**",
            "/"
    };

}
