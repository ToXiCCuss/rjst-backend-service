package de.rjst.rjstbackendservice.security.logic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserSupplier implements Function<String, UserDetails> {

    private final Function<String, List<String>> groupsByUserSupplier;
    private final BiFunction<String, List<String>, UserDetails> userDetailsMapper;


    @Override
    public final UserDetails apply(final String username) {
        final List<String> groups = groupsByUserSupplier.apply(username);
        if (groups.isEmpty()) {
            throw new UsernameNotFoundException("No groups found: " + username);
        }

        final UserDetails result = userDetailsMapper.apply(username, groups);
        return result;
    }
}
