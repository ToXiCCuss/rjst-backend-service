package de.rjst.bes.logic;

import de.rjst.bes.adapter.IpQueryResponse;
import de.rjst.bes.adapter.IpQueryService;
import de.rjst.bes.cache.CacheNames;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IpSearchFunction implements Function<String, IpQueryResponse> {

    private final IpQueryService ipQueryService;

    @Cacheable(value = CacheNames.IP, key = "#ip")
    @Override
    public IpQueryResponse apply(final String ip) {
        return ipQueryService.getIpQueryResponse(ip);
    }
}
