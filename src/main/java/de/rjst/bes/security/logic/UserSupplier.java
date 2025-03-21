package de.rjst.bes.security.logic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static de.rjst.bes.cache.CacheNames.LDAP_GROUPS;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserSupplier implements Function<String, UserDetails> {

    private final GroupsByUserSupplier groupsByUserSupplier;
    private final GroupsMapper userDetailsMapper;


    @Cacheable(value = LDAP_GROUPS, key = "#username")
    @Override
    public UserDetails apply(final String username) {
        final List<String> groups;
        try {
            groups = groupsByUserSupplier.apply(username);
        } catch (final RuntimeException e) {
            throw new UsernameNotFoundException("No groups found: " + username);
        }

        if (groups.isEmpty()) {
            throw new UsernameNotFoundException("No groups found: " + username);
        }

        return userDetailsMapper.apply(username, groups);
    }
}
