package com.careydevelopment.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
 
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/").permitAll();
    }
	
	
	 /*@Autowired
	 DataSource dataSource;
	 */
	
	 @Autowired 
	 UserDetailsService userDetailsService;
	 
	 @Autowired
	 public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {    
		 auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());;
	 } 

	 
	 @Bean(name="passwordEncoder")
	 public PasswordEncoder passwordencoder(){
	     return new BCryptPasswordEncoder();
	 }
	 
	 
	 /*@Autowired
	 public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	   auth.jdbcAuthentication().dataSource(dataSource)
	  .usersByUsernameQuery(
	   "select username,password, enabled from users where username=?")
	  .authoritiesByUsernameQuery(
	   "select username, role from user_roles where username=?");
	 } */
	 
	 
	 
	 /*@Override
	 protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests().antMatchers("/css/**","/images/**","/js/**").permitAll()
		 	.and()
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll();		
	 }*/

		 /*http.authorizeRequests()
	   //.antMatchers("/resources/static/**").permitAll()
	  .antMatchers("/**").access("hasRole('ROLE_ADMIN')")  
	  //.anyRequest().permitAll()
	  .and()
	    .formLogin().loginPage("/login")
	    .usernameParameter("username").passwordParameter("password")
	  .and()
	    .logout().logoutSuccessUrl("/login?logout") 
	   .and()
	   //.exceptionHandling().accessDeniedPage("/403")
	  //.and()
	    .csrf();*/

}
