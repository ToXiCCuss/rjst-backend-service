package de.rjst.bes.security.ldap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class LdapGroupRepositoryImplIT {

    @Autowired
    private LdapGroupRepository underTest;

    @Test
    void findAll() {
        final List<LdapGroup> result = underTest.findAll();
        assertThat(result.size()).isGreaterThan(0);
    }

}
