package de.rjst.rjstbackendservice.jobs;

import de.rjst.rjstbackendservice.database.AdvisoryLock;
import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserProcessSupplier {

    private final PlayerRepository playerRepository;

    @AdvisoryLock(key = 1L)
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60)
    public List<Player> get() {
        var all = playerRepository.findByProcessState(ProcessState.WAITING);
        all.stream().forEach(player -> {
            player.setProcessState(ProcessState.RUNNING);
        });
        return playerRepository.saveAll(all);
    }

}
