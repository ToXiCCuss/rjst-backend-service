package de.rjst.rjstbackendservice.logic;

import de.rjst.rjstbackendservice.database.Player;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player getPlayerById(final Long id) {
        Optional<Player> result = playerRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public Player postPlayer(final Player player) {
        return playerRepository.save(player);
    }

    @Transactional
    @Override
    public Player updatePlayer(final Player player) {
        final Optional<Player> dbPlayer = playerRepository.findById(player.getId());
        final Player result;
        if (dbPlayer.isPresent()) {
            result = dbPlayer.get();
            result.setName(player.getName());
            result.setPassword(player.getPassword());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    @Override
    public boolean deletePlayer(Long id) {
        playerRepository.deleteById(id);
        return true;
    }
}
