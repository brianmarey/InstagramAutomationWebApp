package com.careydevelopment.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

@Configuration
public class AuthenticationProviderConfig {
    
	@Autowired
	DataSource dataSource;
	
    @Bean(name="userDetailsService")
    public UserDetailsService userDetailsService(){
     JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
     jdbcImpl.setDataSource(dataSource);
     jdbcImpl.setUsersByUsernameQuery("select username,password, enabled from users where username=?");
     jdbcImpl.setAuthoritiesByUsernameQuery("select username, role from user_roles where username=?");
     return jdbcImpl;
    }
}