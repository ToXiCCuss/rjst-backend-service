package de.rjst.rjstbackendservice.security.logic;

import de.rjst.rjstbackendservice.security.model.Role;
import de.rjst.rjstbackendservice.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;


@RequiredArgsConstructor
@Service
public class GroupsMapper implements BiFunction<String, List<String>, UserDetails> {


    @Override
    public final UserDetails apply(final String ldapUser, final List<String> groups) {
        final User result = new User();
        result.setUsername(ldapUser);

        final List<Role> roles = groups.stream().map(Role::new).toList();
        result.setRoles(roles);
        return result;
    }
}
