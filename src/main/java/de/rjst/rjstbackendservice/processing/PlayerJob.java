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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerJob {

    private final TransactionOperations jobTransactionOperations;
    private final PlayerRepository playerRepository;
    private final ProcessPlayerFunction processPlayerFunction;

    @Scheduled(fixedDelay = 1_000L)
    public void process() {
        log.info("Processing player data");
        boolean pending = true;
        while (pending) {
            pending = Boolean.TRUE.equals(jobTransactionOperations.execute(txStatus -> {
                final List<PlayerEntity> players = playerRepository.findTop50ByProcessStateOrderByIdAsc(ProcessState.WAITING);
                if (players.isEmpty()) {
                    return false;
                }
                players.forEach(processPlayerFunction::apply);
                return true;
            }));
        }
    }

}
