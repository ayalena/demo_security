package com.example.demo_security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

//    @GetMapping("/")
//    public ResponseEntity<Object> demo() {
//        return ResponseEntity.ok("Security Demo");
//    }

    @GetMapping("/secure")
    public ResponseEntity<Object> demoSecure() {
        return ResponseEntity.ok("Security Demo - ADMIN Only");
    }

    @GetMapping("/users_only")
    public ResponseEntity<Object> demoPublic() {
        return ResponseEntity.ok("Security Demo - User and Admin access");
    }

    @GetMapping("/all") //landingspagina kan ook gewoon in frontend
    public ResponseEntity<Object> demoEverybody() {
        return ResponseEntity.ok("Landingspagina");
    }
}
