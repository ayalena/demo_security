package com.example.controller;

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
        return ResponseEntity.ok("Security Demo - Admin Only");
    }

    @GetMapping("/all")
    public ResponseEntity<Object> demoPublic() {
        return ResponseEntity.ok("Landingspagina - for everybody");
    }

    @GetMapping("/users_only")
    public ResponseEntity<Object> demoEverybody() {
        return ResponseEntity.ok("Security Demo - User and Admin access");
    }


}
