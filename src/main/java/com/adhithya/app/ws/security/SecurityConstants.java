package com.adhithya.app.ws.security;

/*
 * Token generation
 */
public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; // 10 days - token validity
    public static final String TOKEN_PREFIX = "Bearer "; // passed along with header stream in http request
    public static final String HEADER_STRING = "Authorization"; 
    public static final String SIGN_UP_URL = "/users ";
    public static final String TOKEN_SECRET = "Jqqwer23";

//    public static String getTokenSecret()
//    {
//        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
//        return appProperties.getTokenSecret();
//    }
    
}