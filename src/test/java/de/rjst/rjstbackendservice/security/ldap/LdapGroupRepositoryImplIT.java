package de.rjst.rjstbackendservice.security.ldap;

import de.rjst.rjstbackendservice.security.ldap.unit.LdapGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class LdapGroupRepositoryImplIT {

    @Autowired
    private LdapGroupRepositoryImpl underTest;

    @Test
    void findAll() {
        final List<LdapGroup> result = underTest.findAll();
        assertThat(result.size()).isGreaterThan(0);
    }

}
