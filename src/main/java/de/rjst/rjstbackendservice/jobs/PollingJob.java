package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.logging.RequestLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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

    @Scheduled(fixedDelay = 15L, timeUnit = TimeUnit.SECONDS)
    public void poll() {
        final var players = playerSupplier.get();
        try (final ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            if (players != null) {
                for (final Player player : players) {
                    executorService.submit(process(player));
                }
            }
        }
    }

    @RequestLog(key = "playerId", value = "#player.id")
    private Runnable process(final Player player) {
        return () -> {
            playerConsumer.accept(player);
        };
    }
}
