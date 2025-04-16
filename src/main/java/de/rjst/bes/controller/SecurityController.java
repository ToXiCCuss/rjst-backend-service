package de.rjst.bes.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "oauth2")
@RestController
@RequestMapping("security")
public class SecurityController {

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> get() {
        final var context = SecurityContextHolder.getContext();
        final var authentication = context.getAuthentication();
        return new ResponseEntity<>(authentication, HttpStatus.OK);
    }

}
