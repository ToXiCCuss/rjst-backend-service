package de.rjst.rjstbackendservice.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static de.rjst.rjstbackendservice.cache.CacheNames.LDAP_GROUPS;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final long DURATION = 8L;

    @Bean
    public CacheManager cacheManager(final List<Cache> caches) {
        final SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }

    @Bean
    public Cache ldapGroups() {
        return new CaffeineCache(LDAP_GROUPS,
                Caffeine.newBuilder()
                        .expireAfterAccess(DURATION, TimeUnit.HOURS)
                        .build());
    }
 }
