package de.rjst.rjstbackendservice.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class LdapAuthenticationProvider implements AuthenticationProvider {

    private final Function<String, UserDetails> userSupplier;

    private final BiFunction<String, String, Boolean> authenticationFunction;

    @NonNull
    @Override
    public final Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final Object credentials = authentication.getCredentials();
        final String password = credentials.toString();

        if (!authenticationFunction.apply(username, password)) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        final UserDetails user = userSupplier.apply(username);
        return new UsernamePasswordAuthenticationToken(user, credentials, user.getAuthorities());
    }

    @Override
    public final boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
