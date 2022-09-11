package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qqq")
public class Login {
    @GetMapping("/log")
    public String login(){
        return "merhaba dunya";
    }
}
