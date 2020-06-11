package com.adhithya.app.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.adhithya.app.ws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	/*
	 * UserDetailsService - Interface given to us by spring security UserService
	 * extends UserDetailsService and inmplements one of the functions given to us
	 * by the spring security which is loadUserByUsername
	 * 
	 * Since our class extends the UserDetailsService we can make use of it as a variable here 
	 * 
	 */
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	/*
	 * We are overriding functions from WebSecurityConfigurerAdapter to suit our
	 * needs The whole point is to let user sign up to be a public url so that
	 * anyone can create a new account while the rest of the api calls are secured
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll().anyRequest()
				.authenticated();

		/*
		 * any request that matches /users with post shall be permitted any other
		 * requests need to be authenticated
		 */
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * configure the encryption method that we use to encrypt our user passwprd
		 * 
		 */
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
}
