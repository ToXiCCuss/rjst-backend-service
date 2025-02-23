package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class PollingJob {

    private final PlayerSupplier playerSupplier;
    private final PlayerConsumer playerConsumer;

    @Scheduled(fixedDelay = 250L, timeUnit = TimeUnit.MILLISECONDS)
    public void poll() {
        final var players = playerSupplier.get();
        if (players != null && !players.isEmpty()) {
            try (final ExecutorService executorService = Executors.newFixedThreadPool(10)) {
                for (final Player player : players) {
                    executorService.submit(() -> playerConsumer.accept(player));
                }
                executorService.shutdown();
            }
        }
    }
}
