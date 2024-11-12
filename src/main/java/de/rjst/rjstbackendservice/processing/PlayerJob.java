package de.rjst.rjstbackendservice.processing;

import de.rjst.rjstbackendservice.database.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionOperations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerJob {

    private final TransactionOperations jobTransactionOperations;
    private final PlayerRepository playerRepository;
    private final LockRepository lockRepository;

    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.SECONDS)
    public void process() {
        jobTransactionOperations.executeWithoutResult(transactionStatus -> {
            if (lockRepository.tryAdvisoryLock()) {
                log.info("Acquired lock");
                try {
                    Thread.sleep(30000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lockRepository.releaseAdvisoryLock();
                log.info("Released lock");
            } else {
                log.info("Could not acquire lock");
            }
        });
    }

}
