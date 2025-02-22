package de.rjst.rjstbackendservice.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PollingJob {

    private final UserProcessSupplier userProcessSupplier;

    @Scheduled(fixedRate = 500)
    public void poll() {
        final var players = userProcessSupplier.get();
    }

}
