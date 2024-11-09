package de.rjst.rjstbackendservice.logic;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionOperations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RequiredArgsConstructor
@Service
public class TestDataGeneratorFunction implements Function<Long, List<PlayerEntity>> {

    private final TransactionOperations jobTransactionOperations;
    private final PlayerRepository playerRepository;
    private final Executor createTaskExecutor;
    private final Faker faker;

    @Override
    public List<PlayerEntity> apply(final Long amount) {
        List<CompletableFuture<PlayerEntity>> futures = LongStream.range(0, amount)
                .mapToObj(i -> CompletableFuture.supplyAsync(this::generateAndSavePlayer, createTaskExecutor))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private PlayerEntity generateAndSavePlayer() {
        return jobTransactionOperations.execute(status -> {
            final PlayerEntity player = generatePlayer();
            return playerRepository.save(player);
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
