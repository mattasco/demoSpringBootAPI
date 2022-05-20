package com.example.demo.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccueilController {

    @GetMapping("/")
    public String hello(){

        return "Serveur Spring OK";
    }
}
