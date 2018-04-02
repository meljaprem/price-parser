package com.prem.priceparser.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {


    @GetMapping("/login")
    public String loginController(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout){
        return "login";
    }
}
