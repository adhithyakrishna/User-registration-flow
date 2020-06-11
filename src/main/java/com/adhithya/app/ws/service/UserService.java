package com.adhithya.app.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.adhithya.app.ws.shared.dto.UserDto;

/*
 * skeleton of all the user logic
 * 
 * The UserDetailsService is provided to use by spring security
 */

@Component
public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
}
