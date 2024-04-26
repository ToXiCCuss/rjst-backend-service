package de.rjst.rjstbackendservice.security.ldap;

import de.rjst.rjstbackendservice.security.config.SecurityProperties;
import de.rjst.rjstbackendservice.security.ldap.unit.LdapGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchControls;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class LdapGroupRepositoryImpl implements LdapGroupRepository {

    private final SecurityProperties securityProperties;

    private final LdapOperations ldapOperations;

    private final AttributesMapper<LdapGroup> ldapGroupMapper;

    @Override
    public final List<LdapGroup> findAll() {
        final String base = String.format("ou=%s,ou=tool,ou=groups", securityProperties.getToolUser());
        final SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        final String filter = "(objectClass=posixGroup)";

        return ldapOperations.search(base, filter, controls, ldapGroupMapper);
    }
}
