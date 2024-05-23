package com.luisin.jrebel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JRebelController {

    @GetMapping("/jrebel")
    public ResponseEntity<String> jrebel() {
        return ResponseEntity.ok().body("JRebel rocks!");
    }
}
