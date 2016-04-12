package com.careydevelopment.instagramautomation.controller;

import java.io.FileWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.jinstagram.Instagram;
import org.jinstagram.entity.relationships.RelationshipFeed;
import org.jinstagram.model.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.instagramautomation.model.FollowResult;


@RestController
public class FollowUserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FollowUserController.class);
	
	//@Autowired
	//FolloweeRepository followeeRepository;
	
    @RequestMapping(value="/followUser",method = RequestMethod.GET,produces="application/json")
    public FollowResult followUser(@RequestParam(value="id", required=true) String id, 
    		@RequestParam(value="screenName", required=true) String screenName,
    		HttpServletRequest request, Model model) {

    	Instagram instagram = (Instagram)request.getSession().getAttribute("instagram");

		//FollowRun followRun =(FollowRun)request.getSession().getAttribute(CURRENT_FOLLOW_RUN);
		//LOGGER.info("Current follow run is " + followRun);
		
		LOGGER.info("id for follow " + id + " " + screenName);
				
		FollowResult followResult = new FollowResult();
		int followResultCode = 1;
		
		//Followee followee = new Followee();
		
		try {
			RelationshipFeed feed = instagram.setUserRelationship(id, Relationship.FOLLOW);

			LOGGER.info("Meta Code : "       + feed.getMeta().getCode());
			LOGGER.info("Incoming_Status : " + feed.getData().getIncomingStatus());
		    //addToDnf(twitterId);
		    followResult.setMessage("Followed ");
		} catch (Exception e) {
			e.printStackTrace();
			followResult.setMessage("Failed to follow ");
			followResultCode = 2;
		}
		
		/*
		followee.setFollowRun(followRun);
		followee.setScreenName(screenName);
		followee.setStatus(followResultCode);
		
		followeeRepository.save(followee);
		*/
		
        return followResult;
    }
    
    
	/**
	 * Adds the given id to the DNF file so we don't try to follow him again
	 */
    /*private void addToDnf(long id) {
		try {
			PrintWriter f0 = new PrintWriter(new FileWriter(DNF_FILE,true));
			f0.println(""+id);
			f0.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Problem writing ID to file!",e);
		}
	}*/
}

