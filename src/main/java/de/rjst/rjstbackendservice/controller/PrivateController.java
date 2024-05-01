package de.rjst.rjstbackendservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RequiredArgsConstructor
@RestController
@RequestMapping("private")
public class PrivateController {

    private final Supplier<String> testSupplier;

    @GetMapping
    public ResponseEntity<String> getTest3() {
        return ResponseEntity.ok("Hello Any Authenticated! This is private endpoint");
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("user")
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>(testSupplier.get(), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin")
    public  ResponseEntity<String> getTest2() {
        return ResponseEntity.ok(testSupplier.get());
    }
}
