package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
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
                    executorService.submit(() -> {
                        MDC.put("playerId", String.valueOf(player.getId()));
                        try {
                            playerConsumer.accept(player);
                        } finally {
                            MDC.remove("playerId");
                        }
                    });
                }
            }
        }
    }

    public Runnable wrapWithMDC(Runnable task) {
        final Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                task.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
