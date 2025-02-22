package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import de.rjst.rjstbackendservice.logging.RequestLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerConsumer implements Consumer<Player> {

    private final PlayerRepository playerRepository;

    @RequestLog(key = "playerId", value = "#player.id")
    @Override
    public void accept(Player player) {
        player.setProcessState(ProcessState.FINISHED);
        log.info("Processed player {}", player.getId());
        playerRepository.saveAndFlush(player);
    }
}
