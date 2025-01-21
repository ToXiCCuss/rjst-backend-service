package de.rjst.rjstbackendservice.adapter;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "integration", url = "https://rjst-integration.vpn.rjst.de")
public interface IntegrationFeign {

    @PostMapping("/data")
    void sendData(@RequestParam Integer amount, @RequestParam String flowType);

}
