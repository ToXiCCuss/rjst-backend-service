package de.rjst.bes;

import java.util.Base64;


public class TestUtil {

    public static final String ANY_STRING = "anyString";
    public static final Long ANY_LONG = 1L;
    public static final Integer ANY_INTEGER = 1;


    public static String ANY_USER_LOGIN = getAuthHeader("rstenzhorn", "123");

    public static String getAuthHeader(final String username, final String password) {
        final var value = String.format("%s:%s", username,password);
        final var encoded = Base64.getEncoder().encodeToString(value.getBytes());
        return String.format("Basic %s", encoded);
    }
}
