package de.rjst.rjstbackendservice.security.config;

import de.rjst.rjstbackendservice.security.util.LdapUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;

@RequiredArgsConstructor
@Configuration
public class LdapConfig {


    private static final String USER_PATH = "ou=tool,ou=users,dc=rjst,dc=de";
    private static final String BASE_PATH = "dc=rjst,dc=de";

    private final LdapProperties ldapProperties;


    @Bean
    public LdapContextSource contextSource() {
        final String username = LdapUtil.getLdapPath(ldapProperties.getUsername(), USER_PATH);
        final LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapProperties.getUrl());
        contextSource.setBase(BASE_PATH);
        contextSource.setUserDn(username);
        contextSource.setPassword(ldapProperties.getPassword());
        return contextSource;
    }

}
