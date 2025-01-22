package de.rjst.rjstbackendservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rjst.rjstbackendservice.redis.RedisEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/keys")
    public Set<String> getKeys() {
        return redisTemplate.keys("*");
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/value")
    public RedisEntry getValue(@RequestParam final String key) {
        return RedisEntry.builder()
                .ttl(redisTemplate.getExpire(key))
                .value(objectMapper.convertValue(redisTemplate.opsForValue().get(key), Map.class))
                .build();
    }

}
