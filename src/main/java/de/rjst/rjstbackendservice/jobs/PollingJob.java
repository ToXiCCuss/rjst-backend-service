package de.rjst.rjstbackendservice.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PollingJob {

    private final UserProcessSupplier userProcessSupplier;

    @Scheduled(fixedRate = 500)
    public void poll() {
        final var players = userProcessSupplier.get();
    }

}
