package de.rjst.rjstbackendservice.security;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service
public class LdapAuthenticationProvider implements AuthenticationProvider {

    @Qualifier("ldapDetailsService")
    private final UserDetailsService userDetailsService;

    private final BiFunction<String, String, Boolean> authenticationFunction;

    @Override
    public final @NotNull Authentication authenticate(@NotNull final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final Object credentials = authentication.getCredentials();
        final String password = credentials.toString();

        if (!authenticationFunction.apply(username, password)) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        final UserDetails user = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, credentials, user.getAuthorities());
    }

    @Override
    public final boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
