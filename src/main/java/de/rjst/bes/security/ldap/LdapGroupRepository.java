package de.rjst.bes.security.ldap;

import java.util.List;

public interface LdapGroupRepository {

    List<LdapGroup> findAll();


}
