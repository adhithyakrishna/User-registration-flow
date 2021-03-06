package com.adhithya.app.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.adhithya.app.ws.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	/*
	 * UserDetailsService - Interface given to us by spring security UserService
	 * extends UserDetailsService and inmplements one of the functions given to us
	 * by the spring security which is loadUserByUsername
	 * 
	 * Since our class extends the UserDetailsService we can make use of it as a
	 * variable here
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
	 * 
	 * We have added authentication filter to the end of it, the authentication
	 * filter extends usernamepasswordauthentication filter which has
	 * authenticationmanager to help with our authentication
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
				.permitAll().anyRequest().authenticated().and().addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(authenticationManager())).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		/*
		 * any request that matches /users with post shall be permitted any other
		 * requests need to be authenticated
		 * 
		 * session management is configured not create a http session to make it
		 * stateless
		 */
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * configure the encryption method that we use to encrypt our user passwprd we
		 * are configuring the password encoder for our function
		 * 
		 * AuthenticationManagerBuilder helps load user details from our database
		 * AuthenticationManagerBuilder has now access to our user information
		 */
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	/*
	 * Authentication manager by default maps the server to /user, to convert it
	 * into /users/login we'll have to create a new instance of authentication
	 * filter and then set the filterProcessUrl
	 */
	protected AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
	}
}

/*
 * Making the rest api stateless - when there are 5 clients communicating to a
 * server, each client establishes a http session with the server, there are
 * also chances that the clientside browser might cache the credentials such as
 * authorisation token during the session, to make it stateless, spring can be
 * configured to not create http sessions
 * 
 */
