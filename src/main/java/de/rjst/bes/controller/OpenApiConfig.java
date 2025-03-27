package de.rjst.bes.controller;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@RequiredArgsConstructor
@Configuration
public class OpenApiConfig {

    private static final String DESCRIPTION = "description";
    private static final String SCHEME_NAME = "BearerAuth";
    private static final String SCHEME = "bearer";

    private final BuildProperties buildProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(buildProperties.getName())
                        .version(buildProperties.getVersion())
                        .description(buildProperties.get(DESCRIPTION))
                )
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, new SecurityScheme()
                                .name(SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(SCHEME)
                                .bearerFormat("JWT")
                        )
                );
    }


}
