package de.rjst.rjstbackendservice.security.logic.auth;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@RequiredArgsConstructor
@Service
public class AuthenticationFunction implements BiFunction<String, String, Boolean> {

    private static final String PSEUDO = "p";

    private final BiFunction<String, String, Boolean> defaultAuthenticationFunction;
    private final BiFunction<String, String, Boolean> pseudoAuthenticationFunction;

    @Override
    public final Boolean apply(@NotNull final String username, final String password) {
        final boolean result;

        if (username.startsWith(PSEUDO)) {
            result = pseudoAuthenticationFunction.apply(username,password);
        } else {
            result = defaultAuthenticationFunction.apply(username,password);
        }

        return result;
    }
}
