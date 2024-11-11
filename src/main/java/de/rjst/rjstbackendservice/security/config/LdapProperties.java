package de.rjst.rjstbackendservice.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("security.ldap")
@Data
public class LdapProperties {

    private String url;
    private String username;
    private String password;

}
