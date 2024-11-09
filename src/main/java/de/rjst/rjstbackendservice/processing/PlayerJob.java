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

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerJob {

    private final TransactionOperations jobTransactionOperations;
    private final PlayerRepository playerRepository;

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
                players.forEach(entity -> {
                    entity.setCount(entity.getCount() + 1);
                    entity.setProcessState(ProcessState.FINISHED);
                    entity.setPod(System.getenv("HOSTNAME"));
                    playerRepository.save(entity);
                });
                return true;
            }));
        }
    }

}
