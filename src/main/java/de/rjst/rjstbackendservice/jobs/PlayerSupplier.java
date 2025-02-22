package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.AdvisoryLock;
import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerSupplier {

    private final PlayerRepository playerRepository;

    @AdvisoryLock(key = 1L)
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
    public List<Player> get() {
        var all = playerRepository.findByProcessState(ProcessState.WAITING);
        all.stream().forEach(player -> {
            player.setProcessState(ProcessState.RUNNING);
        });
        log.info("Processing {} players", all.size());
        return playerRepository.saveAll(all);
    }

}
