package de.rjst.rjstbackendservice.processing;

import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionOperations;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerJob {

    private final TransactionOperations jobTransactionOperations;
    private final PlayerRepository playerRepository;
    private final Function<PlayerEntity, CompletableFuture<PlayerEntity>> playerFunction;

    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.MINUTES)
    public void process() {
        log.info("Processing player data");
        boolean pending = true;
        while (pending) {
            pending = Boolean.TRUE.equals(jobTransactionOperations.execute(txStatus -> {
                final List<PlayerEntity> players = playerRepository.findTop50ByProcessStateOrderByIdAsc(ProcessState.WAITING);
                if (players.isEmpty()) {
                    return false;
                }
                final List<CompletableFuture<PlayerEntity>> futures = players.parallelStream()
                        .map(playerFunction)
                        .toList();

                CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
                return true;
            }));
        }
    }

}
