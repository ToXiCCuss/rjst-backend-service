package de.rjst.rjstbackendservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("public")
public class PublicController {


    @PreAuthorize("isAnonymous()")
    @GetMapping
    public ResponseEntity<String> getPublic() {
        return new ResponseEntity<>("Hallo", HttpStatus.OK);
    }

}
