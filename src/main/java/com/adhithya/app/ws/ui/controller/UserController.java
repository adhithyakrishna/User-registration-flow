package com.adhithya.app.ws.ui.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adhithya.app.ws.exceptions.UserServiceException;
import com.adhithya.app.ws.security.PropsFromFiles;
import com.adhithya.app.ws.security.SecurityConstants;
import com.adhithya.app.ws.service.UserService;
import com.adhithya.app.ws.shared.dto.UserDto;
import com.adhithya.app.ws.ui.model.request.UserDetailsRequestModel;
import com.adhithya.app.ws.ui.model.response.ErrorMessages;
import com.adhithya.app.ws.ui.model.response.ResponseMessage;
import com.adhithya.app.ws.ui.model.response.UserRest;

import io.jsonwebtoken.Jwts;

/*
 * Register the class as rest controller, which will enable to recieve and send http request
 * Request mapping, mapps the uri
 * 
 * @ConfigurationProperties read the properties from application properties and use them directly
 */
@RestController
@RequestMapping("users")
@ConfigurationProperties(prefix = "users.pagination")
public class UserController {

	@Autowired
	PropsFromFiles propFromFiles;

	@Autowired
	UserService userService;

	int limit = 5;

	public void setlimit(int limit) {
		this.limit = limit;
	}

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

	/*
	 * @Requestbody is to read the json payload that is being sent along with the
	 * request, we'll have to create a java bean with fields that matches with the
	 * request body UserRest is the response model
	 */
	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

		// returned back to the ui
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty()) {
			/*
			 * Creating a specific service exception
			 */
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
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

	/*
	 * The id to be updated should be sent as a path variable The update can take
	 * place only if the user has bearer authorisation token
	 */
	@PutMapping(path = "/{id}", consumes = "application/json")
	public UserRest updateUser(HttpServletRequest request, @PathVariable String id,
			@RequestBody UserDetailsRequestModel userDetails, Principal principal) {

		String emailId = principal.getName();
		UserRest returnValue = new UserRest();
		String token = request.getHeader(SecurityConstants.HEADER_STRING);

		if (userService.validateUser(token, id)) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userDetails, userDto);

			UserDto updateUser = userService.updateUser(id, userDto);
			BeanUtils.copyProperties(updateUser, returnValue);
		} else {
			throw new UserServiceException(ErrorMessages.COULD_NOT_UPDATE_RECORD.getErrorMessage());
		}
		return returnValue;
	}

	@DeleteMapping(path = "/{id}")
	public ResponseMessage deleteUser(HttpServletRequest request, @PathVariable String id) {

		ResponseMessage returnVal = new ResponseMessage();
		String token = request.getHeader(SecurityConstants.HEADER_STRING);

		if (userService.validateUser(token, id) && userService.deleteUser(id)) {
			returnVal.setMessage("User Deleted SuccessFully");
			returnVal.setTimstamp(new Date());
		} else {
			throw new UserServiceException(ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
		}
		return returnVal;
	}

	/*
	 * reading data from the request parameters
	 */
	@GetMapping
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<UserRest>();

		/*
		 * Service method returns DTO
		 */
		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}

		return returnValue;

	}

}
