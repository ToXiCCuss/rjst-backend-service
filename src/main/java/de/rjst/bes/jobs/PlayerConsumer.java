package de.rjst.bes.jobs;

import de.rjst.bes.logging.AsyncProcessingLog;
import de.rjst.bes.database.Player;
import de.rjst.bes.database.PlayerRepository;
import de.rjst.bes.database.ProcessState;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerConsumer implements Consumer<Player> {

    private final PlayerRepository playerRepository;

    @AsyncProcessingLog(key = "playerId", value = "#player.id")
    @Override
    public void accept(final Player player) {
        player.setProcessState(ProcessState.FINISHED);
        player.setThread(Thread.currentThread()
                               .getName());
        log.info("Processed player {}", player.getId());
        playerRepository.saveAndFlush(player);
    }



}
