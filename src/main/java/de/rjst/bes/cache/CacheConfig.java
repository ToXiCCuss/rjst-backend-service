package de.rjst.bes.cache;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

    private final CacheProperties cacheProperties;


    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        final var redisProperties = cacheProperties.getRedis();

        final var serializer = new GenericJackson2JsonRedisSerializer();
        serializer.configure(objectMapper -> {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.registerModule(new JavaTimeModule());
        });
        return RedisCacheConfiguration.defaultCacheConfig()
                                      .entryTtl(redisProperties.getTimeToLive())
                                      .disableCachingNullValues()
                                      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
    }

}
