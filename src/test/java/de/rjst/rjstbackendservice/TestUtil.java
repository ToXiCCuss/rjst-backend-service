package de.rjst.rjstbackendservice;

import lombok.experimental.UtilityClass;

import java.util.Base64;


public class TestUtil {

    public static final String ANY_STRING = "anyString";
    public static final Long ANY_LONG = 1L;
    public static final Integer ANY_INTEGER = 1;


    public static String ANY_USER_LOGIN = Base64.getEncoder().encodeToString("rstenzhorn 123".getBytes());
}
