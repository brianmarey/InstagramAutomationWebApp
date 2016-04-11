package com.careydevelopment.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MyTwitter {
	
	public static final String BRIAN_M_CAREY = "brianmcarey";
	
	private static final String PROPERTIES_FILE_LOCATION = "/etc/tomcat8/resources/twitter.properties";
	
	private static final MyTwitter INSTANCE = new MyTwitter();
	
	private Twitter thisTwitter = null;
	
	
	/**
	 * Create it as a singleton
	 */
	public static final MyTwitter instance() {
		return INSTANCE;
	}

	
	/**
	 * private constructor
	 */
	private MyTwitter() {
	}	
	
	/**
	 * load the properties file containing Twitter credentials
	 */
	private Properties getProperties() {
		Properties props = new Properties();
		
		try {
			File file = new File(PROPERTIES_FILE_LOCATION);
			FileInputStream fis = new FileInputStream(file);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException ("Problem reading properties file!");
		}
		
		return props;
	}
	

	/**
	 * Gets the Twitter object based on provided name
	 */
	public Twitter getTwitter(String name) {
		if (thisTwitter == null) {
			Properties props = getProperties();	
			
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(props.getProperty(name + ".consumerKey"))
			  .setOAuthConsumerSecret(props.getProperty(name + ".consumerSecret"))
			  .setOAuthAccessToken(props.getProperty(name + ".accessToken"))
			  .setOAuthAccessTokenSecret(props.getProperty(name + ".accessSecret"));
			
			TwitterFactory tf = new TwitterFactory(cb.build());
			thisTwitter = tf.getInstance();
		}
		
		return thisTwitter;
	}

}

