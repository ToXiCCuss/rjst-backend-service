package de.rjst.rjstbackendservice.security.logic;

import de.rjst.rjstbackendservice.security.model.RoleDto;
import de.rjst.rjstbackendservice.security.model.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;


@RequiredArgsConstructor
@Service
public class GroupsMapper implements BiFunction<String, List<String>, UserDetails> {


    @Override
    public final @NotNull UserDetails apply(@NotNull final String ldapUser, final @NotNull List<String> groups) {
        final UserDetailsDto result = new UserDetailsDto();
        result.setUsername(ldapUser);

        final List<RoleDto> roles = groups.stream().map(RoleDto::new).toList();
        result.setRoles(roles);
        return result;
    }
}
