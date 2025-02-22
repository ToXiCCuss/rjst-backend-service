package de.rjst.rjstbackendservice.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Component
public class PollingJob {

    private final PlayerSupplier playerSupplier;
    private final PlayerConsumer playerConsumer;

    @Scheduled(fixedDelay = 1000L)
    public void poll() {
        final var players = playerSupplier.get();
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        players.forEach(player -> executorService.submit(() -> playerConsumer.accept(player)));
        executorService.close();
    }

}
