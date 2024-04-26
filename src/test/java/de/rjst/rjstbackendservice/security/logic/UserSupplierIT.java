package de.rjst.rjstbackendservice.security.logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

@SpringBootTest
class UserSupplierIT {

    @Autowired
    private Function<String, UserDetails> underTest;

    @Test
    void apply() {
        UserDetails userDetails = underTest.apply("padmin");
        System.out.println(userDetails);
    }
}
