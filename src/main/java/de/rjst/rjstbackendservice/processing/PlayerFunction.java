package de.rjst.rjstbackendservice.processing;

import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerFunction implements Function<PlayerEntity, CompletableFuture<PlayerEntity>> {

    private final PlayerRepository playerRepository;


    @Async("jobTaskExecutor")
    @Override
    public CompletableFuture<PlayerEntity> apply(final PlayerEntity entity) {
        log.info("Processing player {}", entity.getId());
        entity.setCount(entity.getCount() + 1);
        entity.setProcessState(ProcessState.FINISHED);
        entity.setPod(System.getenv("HOSTNAME"));
        entity.setThread(Thread.currentThread().getName());
        entity.setUpdated(LocalDateTime.now());
        return CompletableFuture.completedFuture(playerRepository.save(entity));
    }
}
