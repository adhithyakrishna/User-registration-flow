package com.adhithya.app.ws.security;

import com.adhithya.app.ws.SpringApplicationContext;

/*
 * Token generation
 */
public class SecurityConstants {
	public static final long EXPIRATION_TIME = 864000000; // 10 days - token validity
	public static final String TOKEN_PREFIX = "Bearer "; // passed along with header stream in http request
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users ";

	/*
	 * Not that the function below is static so it can be accessed anywhere
	 */
	public static String getTokenSecret() {
		/* 
		 * Spring application context would get the bean with the name AppProperties
		 */
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}

}