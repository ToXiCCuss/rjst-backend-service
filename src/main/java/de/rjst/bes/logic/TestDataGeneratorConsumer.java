package de.rjst.bes.logic;

import com.github.javafaker.Faker;
import de.rjst.bes.database.Player;
import de.rjst.bes.database.PlayerRepository;
import de.rjst.bes.database.ProcessState;
import lombok.RequiredArgsConstructor;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class TestDataGeneratorConsumer implements Consumer<Long> {

    private final PlayerRepository playerRepository;
    private final Faker faker;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void accept(final Long amount) {
        for (long i = 0L; i < amount; i++) {
            final Player player = generatePlayer();
            playerRepository.save(player);
        }
    }

    @NonNull
    private Player generatePlayer() {
        Model<Player> playerModel = Instancio.of(Player.class)
                .set(Select.field(Player::getId), null)
                .set(Select.field(Player::getProcessState), ProcessState.WAITING)
                .set(Select.field(Player::getCreated), LocalDateTime.now())
                .set(Select.field(Player::getUpdated), LocalDateTime.now())
                .supply(Select.field(Player::getName), () ->
                        faker.funnyName().name())
                .supply(Select.field(Player::getPassword), () ->
                        faker.internet().password(8, 12, true, true))
                .toModel();
        return Instancio.create(playerModel);
    }
}
