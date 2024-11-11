package de.rjst.rjstbackendservice.logic;

import de.rjst.rjstbackendservice.database.Player;

import java.util.List;

public interface PlayerService {

    List<Player> getPlayers();

    Player getPlayerById(Long id);

    Player postPlayer(Player player);

    Player updatePlayer(Player player);

    boolean deletePlayer(Long id);

}
