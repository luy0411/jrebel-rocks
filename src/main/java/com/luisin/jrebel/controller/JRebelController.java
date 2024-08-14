package com.luisin.jrebel.controller;

import com.luisin.jrebel.service.JRebelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JRebelController {

    private JRebelService service;

    public JRebelController(JRebelService service) {
        this.service = service;
    }

    @GetMapping("/jrebel")
    public ResponseEntity<String> jrebel() {
        return ResponseEntity.ok().body("JRebel rocks, " + service.getName());
    }

}
