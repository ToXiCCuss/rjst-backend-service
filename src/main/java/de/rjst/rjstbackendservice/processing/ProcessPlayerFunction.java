package de.rjst.rjstbackendservice.processing;

import de.rjst.rjstbackendservice.database.PlayerEntity;
import de.rjst.rjstbackendservice.database.PlayerRepository;
import de.rjst.rjstbackendservice.database.ProcessState;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class ProcessPlayerFunction implements Function<PlayerEntity, CompletableFuture<Void>> {

    private final PlayerRepository playerRepository;

    @Async("jobTaskExecutor")
    public CompletableFuture<Void> apply(final PlayerEntity entity) {
        entity.setCount(entity.getCount() + 1);
        entity.setProcessState(ProcessState.FINISHED);
        entity.setPod(System.getenv("HOSTNAME"));
        entity.setThread(Thread.currentThread().getName());
        entity.setUpdated(LocalDateTime.now());
        playerRepository.save(entity);
        return CompletableFuture.completedFuture(null);
    }

}
