package de.rjst.bes.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

@Slf4j
public class RedisCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(final RuntimeException exception, final Cache cache, final Object key) {
        log.warn("Redis cache is unavailable. GET {} {}: {}",
            cache.getName(), key, exception.getMessage());
    }

    @Override
    public void handleCachePutError(final RuntimeException exception, final Cache cache, final Object key, final Object value) {
        log.warn("Redis cache is unavailable. PUT {} {}: {}",
            cache.getName(), key, exception.getMessage());
    }

    @Override
    public void handleCacheEvictError(final RuntimeException exception, final Cache cache, final Object key) {
        log.warn("Redis cache is unavailable. EVICT {} {}: {}",
            cache.getName(), key, exception.getMessage());
    }

    @Override
    public void handleCacheClearError(final RuntimeException exception, final Cache cache) {
        log.warn("Redis cache is unavailable. CLEAR {}: {}",
            cache.getName(), exception.getMessage());
    }
}
