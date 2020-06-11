package com.adhithya.app.ws.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

/*
 * Extends BasicAuthenticationFilter
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	/*
	 * When http request is made and the request is triggered, doFilterInternal
	 * would be called, we'll access the header and the bearer along with jwt is
	 * accessible in header
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String header = req.getHeader(SecurityConstants.HEADER_STRING);

		/*
		 * If the header is null or the header is not starting with bearer we'll
		 * proceeed to do the next filters and return after that
		 */
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		/*
		 * If we have the bearer header
		 */
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		/*
		 * we will set the authentication to the security context and continue doing the
		 * next filters
		 */
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);

		if (token != null) {

			// bearer is removed
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

			/*
			 * compare the jwt recieved from the request along with the token secret that
			 * was used for signing the key, this will give us
			 * 
			 * returns the subject as a string, since we used email as the subject in
			 * authentication filter we will be getting back the email in user
			 */
			String user = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token).getBody()
					.getSubject();

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}

			return null;
		}

		return null;
	}

}
