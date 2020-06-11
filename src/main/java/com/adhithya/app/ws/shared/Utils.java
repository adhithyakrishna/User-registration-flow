package com.adhithya.app.ws.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

//this is a component because it is going to be autowired to userserviceimplementation class

@Component
public class Utils {

	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	// length of our randowm user id
	public String generateUserId(int length) {
		return generateRandomString(length);
	}

	/*
	 * generate string of random characters of given length, we'll randomly pickup
	 * random alphabets n number of times.
	 */
	
	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}

}
