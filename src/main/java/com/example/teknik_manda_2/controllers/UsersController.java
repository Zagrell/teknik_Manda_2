package com.example.teknik_manda_2.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class UsersController {

    @GetMapping("/")
    public String index(){
        return "index.html";
    }



}
