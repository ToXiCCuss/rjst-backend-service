package de.rjst.bes.cluster;


import lombok.experimental.UtilityClass;

import java.net.InetAddress;
import java.net.UnknownHostException;

@UtilityClass
public class HostnameSupplier {

    public String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
