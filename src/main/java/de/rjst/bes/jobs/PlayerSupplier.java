package de.rjst.bes.jobs;

import de.rjst.bes.database.Player;
import de.rjst.bes.database.PlayerRepository;
import de.rjst.bes.database.ProcessState;
import de.rjst.bes.aop.advisorylock.AdvisoryLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlayerSupplier {

    private final PlayerRepository playerRepository;

    @AdvisoryLock(appId = 1)
    public List<Player> get() {
        List<Player> result = new ArrayList<>();
        final var players = playerRepository.findByProcessState(ProcessState.WAITING, PageRequest.of(0, 100));
        if (!players.isEmpty()) {
            log.info("Found {} players to process", players.size());
            players.forEach(player -> player.setProcessState(ProcessState.RUNNING));
            result = playerRepository.saveAllAndFlush(players);
        }
        return result;
    }

}
