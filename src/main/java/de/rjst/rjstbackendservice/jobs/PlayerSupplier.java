package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import de.rjst.rjstbackendservice.aop.advisorylock.AdvisoryLock;
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
        final var players = playerRepository.findByProcessState(ProcessState.WAITING, PageRequest.of(0, 25));
        if (!players.isEmpty()) {
            log.info("Found {} players to process", players.size());
            players.stream().forEach(player -> {
                player.setProcessState(ProcessState.RUNNING);
            });
            result = players;
            playerRepository.saveAllAndFlush(players);
        }
        return result;
    }

}
