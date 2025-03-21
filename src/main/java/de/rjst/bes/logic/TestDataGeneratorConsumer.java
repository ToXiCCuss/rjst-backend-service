package de.rjst.bes.logic;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import de.rjst.bes.database.Player;
import de.rjst.bes.database.PlayerRepository;
import de.rjst.bes.database.ProcessState;
import lombok.RequiredArgsConstructor;
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
        final Player result = new Player();
        result.setProcessState(ProcessState.WAITING);
        final Address address = faker.address();
        result.setName(address.firstName() + " " + address.lastName());
        final Internet internet = faker.internet();
        result.setPassword(internet.password());
        result.setCreated(LocalDateTime.now());
        result.setUpdated(LocalDateTime.now());
        return result;
    }
}
