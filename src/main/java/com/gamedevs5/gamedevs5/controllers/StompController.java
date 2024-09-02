package com.gamedevs5.gamedevs5.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StompController {
    @GetMapping()
    public String getRoot() {
        return "Hej";
    }
}
