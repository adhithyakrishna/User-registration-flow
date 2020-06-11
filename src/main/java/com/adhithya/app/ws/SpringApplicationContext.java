package com.adhithya.app.ws;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*
 * For the spring application context to access the the application context
 * SpringApplicationContext has to be a bean in itself hence we have 
 * declared a function for it in MobileAppWsApplication file
 * 
 *
 */
public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		CONTEXT = context;
	}

	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
}