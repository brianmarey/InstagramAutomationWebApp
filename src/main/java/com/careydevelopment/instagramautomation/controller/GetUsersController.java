package com.careydevelopment.instagramautomation.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jinstagram.Instagram;
import org.jinstagram.entity.tags.TagMediaFeed;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.entity.users.feed.UserFeed;
import org.jinstagram.entity.users.feed.UserFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careydevelopment.instagramautomation.model.UserLightweight;


@RestController
public class GetUsersController {
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUsersController.class);
	
	private static final int COUNT_SIZE = 100;
	
	private Instagram instagram = null;
	private List<String> followers = new ArrayList<String>();
	
	//@Autowired
	//FollowRunRepository followRunRepository;
	
	//@Autowired
	//UserRepository userRepository;
	
    @RequestMapping(value="/getUsers",method = RequestMethod.GET,produces="application/json")
    public List<UserLightweight> getUsers(@RequestParam(value="keyword", required=true) String keywords,
    		HttpServletRequest request, Model model) {
    	
    	//FollowRun followRun = logRun();
    	//request.getSession().setAttribute(CURRENT_FOLLOW_RUN, followRun);
       
    	LOGGER.info("in getUsers");
    	
		instagram = (Instagram)request.getSession().getAttribute("instagram");

		String[] keys = keywords.split(",");
		
		List<UserLightweight> ships = new ArrayList<UserLightweight>();
		
		try {
			UserFeed feed = instagram.getUserFollowList(instagram.getCurrentUserInfo().getData().getId());
			setFollowers(feed);
			
			for (int i=0;i<keys.length;i++) {
				String key = keys[i];
				
				if (key != null && key.trim().length() > 2) {		
					LOGGER.info("Now searching for " + key);
					
					if (key.startsWith("#")) key = key.substring(1,key.length());
					
					TagMediaFeed mediaFeed = instagram.getRecentMediaTags(key);
			    	List<MediaFeedData> mediaFeeds = mediaFeed.getData();
					
					String[] returnedDudes = getReturnedDudes(mediaFeeds);		
					
					/*for (String s : returnedDudes) {
						LOGGER.info(s);
					}*/
					
					//RelationshipFeed feed = instagram.getUserRelationship(m.getUser().getId());
					
					//ResponseList<Friendship> friendships = twitter.lookupFriendships(returnedDudes);
					
					ships.addAll(getLightweights(returnedDudes));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return ships;
    }
    
    
    private void setFollowers(UserFeed feed) {
    	LOGGER.info("Getting followers");
    	
    	List<UserFeedData> users = feed.getUserList();
    	
    	for(UserFeedData data : users) {
    		followers.add(data.getId());
    	}
    }

    //saves the log of the run to the db
    /*private FollowRun logRun() {
    	FollowRun followRun = new FollowRun();
    	
    	String username = SecurityHelper.getUsername();
    	User user = userRepository.findById(username);
    	
    	followRun.setUser(user);
    	
    	FollowRun persisted = followRunRepository.save(followRun);
    	
    	return persisted;
    }*/
    
    
    /**
     * Translates heavyweight Friendship objets from Twitter4j into lightweight objects
     * Easier for JSON
     */
    private List<UserLightweight> getLightweights(String[] ids) throws InstagramException {
    	LOGGER.info("getting lightweights");
    	List<UserLightweight> ships = new ArrayList<UserLightweight>();
    	
    	int count = 0;
    	
    	for (String id : ids) {
    		UserInfo userInfo = instagram.getUserInfo(id);
    		
			UserLightweight light = new UserLightweight();
    		light.setId(userInfo.getData().getId());
			light.setScreenName(userInfo.getData().getUsername());
			
    		//be sure to skip the people who are DNF
    		//if (!dnfIds.contains(friendship.getId())) {
    			if (!followers.contains(id)) {
    				light.setFollowing(false);    				
    			} else {
    				light.setFollowing(true);
    			}
    		/*} else {
    			LOGGER.info("Skipping " + friendship.getScreenName() + " because that user is DNF");
    		}*/
    		
    		ships.add(light);
    		
    		//if (count>10) break;
    	}
    	
    	LOGGER.info("done getting lightweights");
    	
    	return ships;
    }
    
    
	/**
	 * Returns candidates for following
	 */
	private String[] getReturnedDudes(List<MediaFeedData> mediaFeeds) throws InstagramException {
		LOGGER.info("Getting returned dudes");
		
		String[] returnedDudes = new String[mediaFeeds.size()];
		//long[] blocks = twitter.getBlocksIDs().getIDs();

		int i = 0;
		
		for (MediaFeedData media : mediaFeeds) {
	    	returnedDudes[i] = media.getUser().getId();
			i++;
		}
		
		return returnedDudes;
	}

    
	/**
	 * Reads a list of do-not-follows from the file.
	 * That file will be appended with the IDs of people we try to follow here
	 * So we don't keep following the same person over and over
	 */
	/*private List<Long> fetchDnfs() {
		BufferedReader br = null;
		List<Long> dnfIds = new ArrayList<Long>();
		
	    try {
	    	br = new BufferedReader(new FileReader(DNF_FILE));
	        String line = br.readLine();

	        while (line != null) {
	        	if (!line.trim().equals("")) {
			        Long id = new Long(line);
			        dnfIds.add(id);
	        	}

	            line = br.readLine();
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		br.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    return dnfIds;
	}*/
}
