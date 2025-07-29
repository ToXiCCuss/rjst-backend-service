package de.rjst.bes.datasource;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CacheCleaner implements Runnable {

    private final CacheManager cacheManager;

    @Override
    public void run() {
        final var cacheNames = cacheManager.getCacheNames();
        cacheNames.forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }
}
