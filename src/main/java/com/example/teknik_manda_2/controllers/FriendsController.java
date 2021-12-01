package com.example.teknik_manda_2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FriendsController {


    @GetMapping("/")
    public String index(){
        return "html/index.html";
    }


    @PostMapping("/")
    public String something(){
        return "";
    }




}
