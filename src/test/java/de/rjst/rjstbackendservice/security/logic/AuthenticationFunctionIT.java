package de.rjst.rjstbackendservice.security.logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.BiFunction;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AuthenticationFunctionIT {

    @Autowired
    private BiFunction<String, String, Boolean> underTest;

    @Test
    void applyTrue() {
        final Boolean result = underTest.apply("cn=rstenzhorn,ou=team,ou=users,dc=rjst,dc=de", "123");
        assertThat(result).isTrue();
    }

    @Test
    void applyFalse() {
        final Boolean result = underTest.apply("rstenzhorn", "1234");
        assertThat(result).isFalse();
    }
}
