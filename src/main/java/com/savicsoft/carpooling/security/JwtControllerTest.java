package com.savicsoft.carpooling.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class JwtControllerTest {
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Logged in and secured");
    }
}
