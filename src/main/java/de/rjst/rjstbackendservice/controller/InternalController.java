package de.rjst.rjstbackendservice.controller;

import de.rjst.rjstbackendservice.adapter.IntegrationFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal")
public class InternalController {

    private final IntegrationFeign integrationFeign;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/data")
    public void sendData() {
        integrationFeign.sendData(1, "A");
    }


}
