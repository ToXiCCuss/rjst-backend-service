package de.rjst.bes;

import java.util.Base64;
import lombok.experimental.UtilityClass;


@UtilityClass
public class TestUtil {

    public final String ANY_STRING = "anyString";
    public final Long ANY_LONG = 1L;
    public final Integer ANY_INTEGER = 1;

    public String getAuthHeader(final String username, final String password) {
        final var value = String.format("%s:%s", username, password);
        final var encoded = Base64.getEncoder()
                                  .encodeToString(value.getBytes());
        return String.format("Basic %s", encoded);
    }
}
