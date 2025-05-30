package de.rjst.bes.container;

import org.mockserver.client.MockServerClient;
import org.testcontainers.utility.DockerImageName;


public class ContainerImages {

    public static final DockerImageName MOCK_SERVER = DockerImageName
            .parse("harbor.vpn.rjst.de/mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());

    public static final DockerImageName POSTGRESQL = DockerImageName.parse("harbor.vpn.rjst.de/postgres:17.5");

}
