package de.rjst.rjstbackendservice.controller;

import de.rjst.rjstbackendservice.redis.RedisEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/keys")
    public Set<String> getKeys() {
        return redisTemplate.keys("*");
    }

    @GetMapping("/value")
    public RedisEntry getValue(@RequestParam final String key) {
        return RedisEntry.builder()
                .ttl(redisTemplate.getExpire(key))
                .value(redisTemplate.opsForValue().get(key))
                .build();
    }

}
