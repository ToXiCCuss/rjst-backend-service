package de.rjst.bes.security.ldap;

import de.rjst.bes.security.config.LdapProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchControls;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LdapGroupRepositoryImpl implements LdapGroupRepository {

    private final LdapProperties ldapProperties;
    private final LdapOperations ldapOperations;
    private final AttributesMapper<LdapGroup> ldapGroupMapper;

    @Override
    public final List<LdapGroup> findAll() {
        final String base = String.format("ou=%s,ou=tool,ou=groups", ldapProperties.getUsername());
        final SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        final String filter = "(objectClass=posixGroup)";

        return ldapOperations.search(base, filter, controls, ldapGroupMapper);
    }
}
