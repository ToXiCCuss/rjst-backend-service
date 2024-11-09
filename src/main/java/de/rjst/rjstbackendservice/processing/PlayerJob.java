package de.rjst.rjstbackendservice.processing;

import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionOperations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerJob {

    private final TransactionOperations jobTransactionOperations;
    private final PlayerRepository playerRepository;
    private final Executor jobTaskExecutor;

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
                players.forEach(player -> {
                    log.info("Processing player {}", player.getId());
                    player.setCount(player.getCount() + 1);
                    player.setProcessState(ProcessState.FINISHED);
                    player.setPod(System.getenv("HOSTNAME"));
                    player.setThread(Thread.currentThread().getName());
                    player.setUpdated(LocalDateTime.now());
                    playerRepository.save(player);
                });
                return true;
            }));
        }
    }



}
