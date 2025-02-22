package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.advisorylock.AdvisoryLock;
import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerSupplier {

    private final PlayerRepository playerRepository;

    @AdvisoryLock(key = 1L)
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
    public List<Player> get() {
        List<Player> result = new ArrayList<>();
        final var players = playerRepository.findByProcessState(ProcessState.WAITING);
        if (!players.isEmpty()) {
            log.info("Found {} players to process", players.size());
            players.stream().forEach(player -> {
                player.setProcessState(ProcessState.RUNNING);
            });
            result = players;
        }
        return result;
    }

}
