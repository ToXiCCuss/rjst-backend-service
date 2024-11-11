package de.rjst.rjstbackendservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ip")
public class IpController {

    @GetMapping
    public String getIp(final HttpServletRequest request) {
        final String clientIp = request.getRemoteAddr();
        return "Your IP address is " + clientIp;
    }

}
