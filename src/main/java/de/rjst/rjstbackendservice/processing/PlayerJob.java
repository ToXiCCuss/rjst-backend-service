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
    private final AuditRepository auditRepository;

    @Scheduled(fixedDelay = 1L, timeUnit = TimeUnit.SECONDS)
    public void process() {
        boolean pending = true;
        while (pending) {
            pending = Boolean.TRUE.equals(jobTransactionOperations.execute(txStatus -> {
                final List<PlayerEntity> players = playerRepository.findTop50ByProcessStateOrderByIdAsc(ProcessState.WAITING);
                if (players.isEmpty()) {
                    return false;
                }
                players.forEach(player -> {
                    log.info("Processing player {}", player.getId());
                    player.setProcessState(ProcessState.FINISHED);
                    player.setPod(System.getenv("HOSTNAME"));
                    player.setThread(Thread.currentThread().getName());
                    player.setUpdated(LocalDateTime.now());
                    playerRepository.save(player);
                    audit(player);
                });
                return true;
            }));
        }
    }

    private void audit(final PlayerEntity player) {
        final AuditEntity audit = new AuditEntity();
        audit.setName(player.getName());
        audit.setCreated(LocalDateTime.now());
        auditRepository.save(audit);
    }


}
