package de.rjst.rjstbackendservice.security.ldap;

import de.rjst.rjstbackendservice.security.ldap.unit.LdapGroup;
import org.jetbrains.annotations.NotNull;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.List;

@Service
public class LdapGroupMapper implements AttributesMapper<LdapGroup> {

    @Override
    public final LdapGroup mapFromAttributes(@NotNull final Attributes attributes) throws NamingException {
        final LdapGroup group = new LdapGroup();
        group.setGroupName((String) attributes.get("cn").get());
        final Attribute memberUidAttribute = attributes.get("memberUid");
        if (memberUidAttribute != null) {
            final List<String> members = new ArrayList<>();
            final NamingEnumeration<?> memberEnum = memberUidAttribute.getAll();
            while (memberEnum.hasMore()) {
                members.add((String) memberEnum.next());
            }
            group.setMembers(members);
        }

        return group;
    }
}
