package de.rjst.rjstbackendservice.config;

import de.rjst.rjstbackendservice.controller.soap.WebService;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SoapConfig {

    private final Bus bus;
    private final WebService webService;


    @Bean
    public EndpointImpl endpoint() {
        EndpointImpl result = new EndpointImpl(bus, webService);
        result.publish("/test");
        return result;
    }

}
