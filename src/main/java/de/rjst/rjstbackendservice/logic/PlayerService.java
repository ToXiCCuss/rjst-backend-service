package de.rjst.rjstbackendservice.logic;

import de.rjst.rjstbackendservice.database.PlayerEntity;

import java.util.List;

public interface PlayerService {

    List<PlayerEntity> getPlayers();

    PlayerEntity getPlayerById(Long id);

    PlayerEntity postPlayer(PlayerEntity player);

    PlayerEntity updatePlayer(PlayerEntity player);

    boolean deletePlayer(Long id);

}
