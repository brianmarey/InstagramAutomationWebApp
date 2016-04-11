package com.careydevelopment.instagramautomation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.jinstagram.Instagram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AutoFollowController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoFollowController.class);
	
	private static final String LOCAL_HOST_FILE = "/etc/tomcat8/resources/localhost.properties";
	
	
    @RequestMapping("/autofollow")
    public String autofollow(HttpServletRequest request, Model model) {    	
    	model.addAttribute("localhost",getLocalHostPrefix());
    	model.addAttribute("autofollowActive", "active");
    	
    	Instagram instagram = (Instagram)request.getSession().getAttribute("instagram");
    	if (instagram == null) {
    		return "redirect:/";
    	}
    	
        return "autofollow";
    }
    
    
    /**
     * Necessary to prevent cross-domain problems with AJAX
     */
    private String getLocalHostPrefix() {
    	try {
	    	Properties props = new Properties();
	    	
	    	File file = new File(LOCAL_HOST_FILE);
	    	FileInputStream inStream = new FileInputStream(file);
	    	
	    	props.load(inStream);
	    	
	    	String localHostPrefix = props.getProperty("localhost.prefix");
	    	return localHostPrefix;
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOGGER.error("Problem reading localhost file!");
    		throw new RuntimeException("Problem reading localhost file!");
    	}
    }
    
}
