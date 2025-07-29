package de.rjst.bes.datasource;

import lombok.experimental.UtilityClass;
import org.testcontainers.containers.ContainerState;

@UtilityClass
public class ContainerPauser {

    public static void pause(final ContainerState container) {
        final var dockerClient = container.getDockerClient();
        dockerClient.pauseContainerCmd(container.getContainerId())
                    .exec();
    }

    public static void unpause(final ContainerState container) {
        final var dockerClient = container.getDockerClient();
        dockerClient.unpauseContainerCmd(container.getContainerId())
                    .exec();
    }

}
