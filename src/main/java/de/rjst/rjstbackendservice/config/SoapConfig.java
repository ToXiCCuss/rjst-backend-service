package de.rjst.rjstbackendservice.config;

import de.rjst.rjstbackendservice.controller.soap.TestWebService;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SoapConfig {

    private final Bus bus;
    private final TestWebService testWebService;


    @Bean
    public EndpointImpl endpoint() {
        EndpointImpl result = new EndpointImpl(bus, testWebService);
        result.publish("/test");
        return result;
    }

}
