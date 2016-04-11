package com.careydevelopment.instagramautomation.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	 
    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
