package de.rjst.rjstbackendservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@RequiredArgsConstructor
@Service
public class LdapDetailsService implements UserDetailsService {

    private final Function<String, UserDetails> userSupplier;

    @Override
    public final UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userSupplier.apply(username);
    }
}
