package de.rjst.bes.controller;

import de.rjst.bes.security.SecurityProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class OpenApiConfig {

    private static final String DESCRIPTION = "description";

    private final BuildProperties buildProperties;
    private final SecurityProperties securityProperties;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(buildProperties.getName())
                        .version(buildProperties.getVersion())
                        .description(buildProperties.get(DESCRIPTION))
                )
                .components(new Components()
                        .addSecuritySchemes("oauth2", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new OAuthFlows()
                                        .authorizationCode(new OAuthFlow()
                                                .authorizationUrl(securityProperties.getAuthorizationUrl())
                                                .tokenUrl(securityProperties.getTokenUrl())
                                                .scopes(new io.swagger.v3.oas.models.security.Scopes()
                                                        .addString("openid", "OpenID Connect")
                                                        .addString("profile", "Profil-Informationen")
                                                        .addString("email", "E-Mail-Informationen")
                                                        .addString("roles", "Benutzerrollen"))))));
    }
}
