package de.rjst.rjstbackendservice.security.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LdapUtil {

    public String getLdapPath(final String username, final String basePath) {
        return String.format("cn=%s,%s", username, basePath);
    }

}
