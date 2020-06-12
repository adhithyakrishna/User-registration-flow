package com.adhithya.app.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.adhithya.app.ws.shared.dto.UserDto;
import com.adhithya.app.ws.ui.model.request.UserDetailsRequestModel;

/*
 * skeleton of all the user logic
 * 
 * The UserDetailsService is provided to use by spring security
 */

@Component
public interface UserService extends UserDetailsService{
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String id, UserDto userDto);
	boolean deleteUser(String id);
	boolean validateUser(String token, String currentUserId);
	List<UserDto> getUsers(int page, int limit);
}
