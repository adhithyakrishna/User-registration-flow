package com.adhithya.app.ws.ui.controller;

import com.adhithya.app.ws.service.UserService;
import com.adhithya.app.ws.shared.dto.UserDto;
import com.adhithya.app.ws.ui.model.request.*;
import com.adhithya.app.ws.ui.model.response.UserRest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users") 
public class UserController {
	
	@Autowired
	UserService userService;
	
	//we need to bind to http request
	@GetMapping
	public String getUser()
	{
		return "get user was called";
	}
	
	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails)
	{
		UserRest returnValue = new UserRest();
		
		// this created userDto would be transfered across different layers
		// data sent as a payload would be present in userDetails
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto); //used to copy user information to userDetails
	
		//userService would take care of the business logic of creating a new user
		//it has to be autowired
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@PutMapping
	public String updateUser()
	{
		return "update request was called";
	}
	
	@DeleteMapping
	public String deleteUser()
	{
		return "delete user was called";
	}
}
