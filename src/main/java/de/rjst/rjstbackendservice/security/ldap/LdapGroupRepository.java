package de.rjst.rjstbackendservice.security.ldap;

import de.rjst.rjstbackendservice.security.ldap.unit.LdapGroup;

import java.util.List;

public interface LdapGroupRepository {

    List<LdapGroup> findAll();


}
