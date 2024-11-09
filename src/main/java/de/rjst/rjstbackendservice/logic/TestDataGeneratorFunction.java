package de.rjst.rjstbackendservice.logic;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class TestDataGeneratorFunction implements Function<Long, List<PlayerEntity>> {

    private final TransactionOperations jobTransactionOperations;
    private final PlayerRepository playerRepository;
    private final Faker faker;

    @Override
    public List<PlayerEntity> apply(final Long amount) {
        return jobTransactionOperations.execute(status -> {
            final List<PlayerEntity> result = new ArrayList<>();
            for (long i = 0L; i < amount; i++) {
                final PlayerEntity player = generatePlayer();
                final PlayerEntity saved = playerRepository.save(player);
                result.add(saved);
            }
            return result;
        });
    }

    private PlayerEntity generatePlayer() {
        final PlayerEntity result = new PlayerEntity();
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
