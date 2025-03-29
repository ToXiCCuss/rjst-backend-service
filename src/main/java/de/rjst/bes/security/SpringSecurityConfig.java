package de.rjst.bes.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SpringSecurityConfig {

    private static final String CLAIM = "roles";
    private static final String ROLE_PREFIX = "ROLE_";
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
       if (securityProperties.getEnabled()) {
           http.authorizeHttpRequests(authorize -> authorize
                           .requestMatchers(securityProperties.getPermitAll()).permitAll()
                           .anyRequest().authenticated()
                   )
                   .oauth2Login(oauth2 -> oauth2
                           .userInfoEndpoint(userInfo -> userInfo
                                   .userAuthoritiesMapper(userAuthoritiesMapper())
                           ))
                   .oauth2ResourceServer(oauth2 -> oauth2
                           .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                   )
                   .csrf(AbstractHttpConfigurer::disable)
                   .cors(Customizer.withDefaults())
                   .sessionManagement(session -> session
                           .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       } else {
              http.authorizeHttpRequests(authorize -> authorize
                            .anyRequest().permitAll()
                     )
                     .csrf(AbstractHttpConfigurer::disable)
                     .cors(AbstractHttpConfigurer::disable)
                     .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       }
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        final var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(CLAIM);
        grantedAuthoritiesConverter.setAuthorityPrefix(ROLE_PREFIX);
        final var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> jwt.getClaimAsStringList(CLAIM).stream()
                .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.toUpperCase())).collect(Collectors.toSet()));
        return jwtAuthenticationConverter;
    }


    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            final Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (authority instanceof final OidcUserAuthority oidcUserAuthority) {
                    final OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
                    final Map<String, Object> claims = userInfo.getClaims();
                    if (claims.containsKey(CLAIM)) {
                        final List<String> groups = (List<String>) claims.get(CLAIM);
                        groups.forEach(group -> mappedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + group.toUpperCase())));
                    }
                }
            });
            return mappedAuthorities;
        };
    }


}
