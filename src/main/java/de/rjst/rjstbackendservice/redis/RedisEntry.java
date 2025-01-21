package de.rjst.rjstbackendservice.redis;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RedisEntry {

    private Long ttl;
    private Object value;

}
