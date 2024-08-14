package com.luisin.jrebel.controller;

import com.luisin.jrebel.service.JRebelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws")
public class AWSController {

    private JRebelService service;

    public AWSController(JRebelService service) {
        this.service = service;
    }

    @GetMapping
    public String name(@RequestParam(name = "checked", required = false, defaultValue = "false") Boolean check) {
        return "AWS rocks too, " + service.getName() + " - check : " + check;
    }
}
