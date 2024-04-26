package de.rjst.rjstbackendservice.controller;


import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("public")
public class PublicController {


    @GetMapping
    @Timed("endpoint.test")
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>("Hallo Nico", HttpStatus.OK);
    }

}
