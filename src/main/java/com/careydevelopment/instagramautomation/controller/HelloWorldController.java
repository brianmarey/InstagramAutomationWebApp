package com.careydevelopment.instagramautomation.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {
	
	private static final Logger LOGGER = Logger.getLogger(HelloWorldController.class);
 
    @RequestMapping("/hello")
    public String hello(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    	LOGGER.info("\n\nIn hello!!!!\n\n");
        model.addAttribute("name", name);
        return "greeting";
    }

}
