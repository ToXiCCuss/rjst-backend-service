package de.rjst.rjstbackendservice.controller.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.springframework.security.access.prepost.PreAuthorize;


@WebService(targetNamespace = "http://test.rjst.de", name = "TestWebService")
public interface TestWebService {

    @PreAuthorize("hasAuthority('USER')")
    @WebMethod
    @WebResult(name = "result")
    String getHelloWorld(@WebParam(name = "input") String name);

}
