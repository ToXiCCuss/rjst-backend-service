package de.rjst.rjstbackendservice.security.logic.auth;

import de.rjst.rjstbackendservice.security.util.LdapUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.directory.DirContext;
import java.util.function.BiFunction;

@Slf4j
@RequiredArgsConstructor
@Service
public class PseudoAuthenticationFunction implements BiFunction<String, String, Boolean> {

    private final LdapTemplate ldapTemplate;
    private static final String BASE_PATH = "ou=pseudo,ou=users,dc=rjst,dc=de";

    @Override
    public final Boolean apply(final String username, final String password) {
        final String user = LdapUtil.getLdapPath(username, BASE_PATH);
        boolean result = true;
        try {
            final ContextSource contextSource = ldapTemplate.getContextSource();
            final DirContext dirContext = contextSource.getContext(user, password);
            dirContext.close();
        } catch (final Exception ex) {
            result = false;
            log.error("Authentication failed: {}", ex.getMessage());
        }

        return result;
    }
}
