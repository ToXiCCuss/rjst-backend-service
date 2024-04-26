package de.rjst.rjstbackendservice.security.ldap.unit;


import lombok.*;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public final class LdapGroup {

    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String groupName;

    @Attribute(name = "memberUid")
    private List<String> members;

}
