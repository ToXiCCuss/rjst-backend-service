package de.rjst.bes.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public/ip")
public class IpController {

    @GetMapping
    public String getIp(final HttpServletRequest request) {
        return request.getRemoteAddr();
    }

}
