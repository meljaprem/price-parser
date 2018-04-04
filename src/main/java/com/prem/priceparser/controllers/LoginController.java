package com.prem.priceparser.controllers;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PropertySource("classpath:messages.properties")
@Data
@Slf4j
public class LoginController {

    @Value(value = "${login.error:Unexpected error}")
    private String error_message;
    @Value(value = "${logout.successful:Good bye!}")
    private String logout_message;

    @GetMapping("/login")
    public ModelAndView loginController(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            ModelAndView modelAndView) {
        log.info("Error message: {}, Logout message: {}", error_message, logout_message);
        if (error != null) {
            modelAndView.addObject("error", error_message);
        } else if (logout != null) {
            modelAndView.addObject("logout", logout_message);
        }
        return modelAndView;
    }
}
