package de.rjst.rjstbackendservice.controller.soap;

import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.logic.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WebServiceImpl implements WebService {

    private final PlayerService playerService;

    @Override
    public List<PlayerEntity> getPlayers() {
        return playerService.getPlayers();
    }

    @Override
    public PlayerEntity getPlayerById(final Long id) {
        return playerService.getPlayerById(id);
    }

    @Override
    public PlayerEntity postPlayer(final PlayerEntity player) {
        return playerService.postPlayer(player);
    }

    @Override
    public PlayerEntity updatePlayer(final PlayerEntity player) {
        return playerService.updatePlayer(player);
    }

    @Override
    public boolean deletePlayer(final Long id) {
        return playerService.deletePlayer(id);
    }
}
