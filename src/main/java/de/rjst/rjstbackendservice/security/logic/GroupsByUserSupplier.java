package de.rjst.rjstbackendservice.security.logic;

import de.rjst.rjstbackendservice.security.ldap.LdapGroupRepository;
import de.rjst.rjstbackendservice.security.ldap.unit.LdapGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Slf4j
@RequiredArgsConstructor
@Service
public class GroupsByUserSupplier implements Function<String, List<String>> {

    private final LdapGroupRepository ldapGroupRepository;

    @Override
    public final @NotNull List<String> apply(final String username) {
        final List<String> result = new ArrayList<>();
        final List<LdapGroup> groups = ldapGroupRepository.findAll();

        for (final LdapGroup group : groups) {
            if (isGroupMember(group, username)) {
                result.add(group.getGroupName());
            }
        }
        return result;
    }

    private static boolean isGroupMember(final @NotNull LdapGroup group, final CharSequence username) {
        boolean result = false;
        final List<String> members = group.getMembers();
        for (final String member : members) {
            if (member.contains(username)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
