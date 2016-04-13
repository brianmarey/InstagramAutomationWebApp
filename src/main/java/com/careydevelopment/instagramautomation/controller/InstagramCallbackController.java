package com.careydevelopment.instagramautomation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.relationships.RelationshipFeed;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InstagramCallbackController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InstagramCallbackController.class);
 
	//http://localhost:8080/InstagramAutomation/instagramCallback
    @RequestMapping("/instagramCallback")
    public String instagramCallback(@RequestParam(value="code", required=true) String code,
    		HttpServletRequest request, Model model) {
    	
    	//get the objects from the session
    	InstagramService service = (InstagramService)request.getSession().getAttribute("instagramservice");
    	
        try {
        	
        	Verifier verifier = new Verifier(code);
        	Token accessToken = service.getAccessToken(verifier);
        	
        	Instagram instagram = new Instagram(accessToken);
        	
        	request.getSession().setAttribute("instagram", instagram);
        	
        	//AccessToken token = auth.build(code);
        	//InstagramSession session = new InstagramSession(token);
        	
        	//LOGGER.info("session is " + session);
        	
        	//request.getSession().setAttribute("instagramsession", session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Problem getting token!");
        }
        
        return "redirect:loggedInPage";
    }

    
    @RequestMapping("/loggedInPage")
    public String loggedInPage(HttpServletRequest request, Model model) {
    	try {
	    	Instagram instagram = (Instagram)request.getSession().getAttribute("instagram");
	    	
	    	model.addAttribute("screenName",instagram.getCurrentUserInfo().getData().getUsername());
	    	
	    	/*TagMediaFeed mediaFeed = instagram.getRecentMediaTags("TrumpTrain");
	    	List<MediaFeedData> mediaFeeds = mediaFeed.getData();
	    	
	    	for (MediaFeedData m : mediaFeeds) {
	    		LOGGER.info(m.getUser().getUserName());
	    		RelationshipFeed feed = instagram.getUserRelationship(m.getUser().getId());

	    		LOGGER.info("incoming_status : " + feed.getData().getIncomingStatus());
	    		LOGGER.info("outgoing_status : " + feed.getData().getOutgoingStatus());
	    	}*/
	    	
	    	//model.addAttribute("screenName",screenName);
    	} catch (Exception e) {
    		e.printStackTrace();
    		LOGGER.error("Problem after Instagram login!");
    	}
    	
    	return "loggedInPage";
    }
}
