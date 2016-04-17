package com.careydevelopment.instagramautomation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.oauth.InstagramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.careydevelopment.propertiessupport.PropertiesFactory;
import com.careydevelopment.propertiessupport.PropertiesFile;

@Controller
public class GetTokenController {
	
	private static final Logger LOGGER = Logger.getLogger(GetTokenController.class);
	
	
    @RequestMapping("/getToken")
    public RedirectView getToken(HttpServletRequest request,Model model) {
    	//this will be the URL that we take the user to
    	String instagramUrl = "";
    	
		try {
			Properties props = PropertiesFactory.getProperties(PropertiesFile.INSTAGRAM_PROPERTIES);
			
			String clientId = props.getProperty("client.id");
			String clientSecret = props.getProperty("client.secret");
			
			String callbackUrl = getCallbackUrl();
			
			InstagramService service = new InstagramAuthService()
                    .apiKey(clientId)
                    .apiSecret(clientSecret)
                    .callback(callbackUrl) 
                    .scope("relationships")
                    .build();
			
			request.getSession().setAttribute("instagramservice", service);

			instagramUrl = service.getAuthorizationUrl();
			
			LOGGER.info("Authorization url is " + instagramUrl);
		} catch (Exception e) {
			LOGGER.error("OAuth problem!",e);
		}
    	
		RedirectView redirectView = new RedirectView();
	    redirectView.setUrl(instagramUrl);
	    
	    return redirectView;
    }

    
    private String getCallbackUrl() {
    	String callbackUrl = "";
    	
    	try {
	    	StringBuilder sb = new StringBuilder();

	    	Properties props = PropertiesFactory.getProperties(PropertiesFile.LOCALHOST_PROPERTIES);
	    	
			String prefix = props.getProperty("localhost.prefix");
			sb.append(prefix);
			sb.append("/InstagramAutomation/instagramCallback");
			
			callbackUrl = sb.toString();
			
			//LOGGER.info("Callback URL is " + callbackUrl);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("Problem setting callback URL!");
    	}
    	
    	return callbackUrl;
    }
}
