package de.rjst.bes.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ipQuery")
public interface IpQueryService {

    @GetMapping("{ip}")
    IpQueryResponse getIpQueryResponse(@PathVariable String ip);

}
