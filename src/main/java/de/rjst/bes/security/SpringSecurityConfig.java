package de.rjst.bes.security;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public static final String CLAIM = "roles";
    public static final String ROLE_PREFIX = "ROLE_";
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final GrantedAuthoritiesMapper authoritiesMapper) throws Exception {
        if (Boolean.TRUE.equals(securityProperties.getEnabled())) {
            http.authorizeHttpRequests(authorize -> authorize.requestMatchers(securityProperties.getPermitAll())
                                                             .permitAll()
                                                             .anyRequest()
                                                             .authenticated())
                .oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo.userAuthoritiesMapper(authoritiesMapper)))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        } else {
            http.authorizeHttpRequests(authorize -> authorize.anyRequest()
                                                             .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        }
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        final var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(CLAIM);
        grantedAuthoritiesConverter.setAuthorityPrefix(ROLE_PREFIX);
        final var result = new JwtAuthenticationConverter();
        result.setJwtGrantedAuthoritiesConverter(jwt -> jwt.getClaimAsStringList(CLAIM)
                                                           .stream()
                                                           .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase()))
                                                           .collect(Collectors.toSet()));
        return result;
    }
}
