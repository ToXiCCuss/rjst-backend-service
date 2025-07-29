package de.rjst.bes.container.config;

import lombok.experimental.UtilityClass;
import org.mockserver.client.MockServerClient;
import org.testcontainers.utility.DockerImageName;

@UtilityClass
public class ContainerImages {

    public static final DockerImageName MOCK_SERVER = DockerImageName
        .parse("harbor.vpn.rjst.de/docker.io/mockserver/mockserver")
        .withTag("mockserver-" + MockServerClient.class.getPackage()
                                                       .getImplementationVersion())
        .asCompatibleSubstituteFor("mockserver/mockserver");

    public static final DockerImageName POSTGRESQL = DockerImageName.parse("harbor.vpn.rjst.de/docker.io/postgres:17.5")
                                                                    .asCompatibleSubstituteFor("postgres");

    public static final DockerImageName REDIS = DockerImageName.parse("harbor.vpn.rjst.de/docker.io/redis:7.4.3");

}
