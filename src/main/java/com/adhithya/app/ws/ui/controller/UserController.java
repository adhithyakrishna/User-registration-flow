package com.adhithya.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhithya.app.ws.service.UserService;
import com.adhithya.app.ws.shared.dto.UserDto;
import com.adhithya.app.ws.ui.model.request.UserDetailsRequestModel;
import com.adhithya.app.ws.ui.model.response.UserRest;

/*
 * Register the class as rest controller, which will enable to recieve and send http request
 * Request mapping, mapps the uri
 */
@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;

	/*
	 * http get request binding is done by @GetMapping creating a submapping of
	 * sorts by pattern mathing
	 */
	@GetMapping(path = "/{id}")
	public UserRest getUser(@PathVariable String id) {

		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);

		return returnValue;
	}

	/*
	 * http post request binding is done by @GetMapping
	 */
	@PostMapping
	/*
	 * @Requestbody is to read the json payload that is being sent along with the
	 * request, we'll have to create a java bean with fields that matches with the
	 * request body UserRest is the response model
	 */
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {

		// returned back to the ui
		UserRest returnValue = new UserRest();

		// this created userDto would be transfered across different layers
		// data sent as a payload would be present in userDetails

		UserDto userDto = new UserDto();
		// copyProperties(source, destination)
		BeanUtils.copyProperties(userDetails, userDto); // used to copy user information to userDetails

		/*
		 * userService would take care of the business logic of creating a new user.
		 * UserService is an interface . UserServiceImpl implements the interface, it
		 * has to be autowired to access the logic
		 */
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}

	@PutMapping
	public String updateUser() {
		return "update request was called";
	}

	@DeleteMapping
	public String deleteUser() {
		return "delete user was called";
	}
}
