package com.adhithya.app.ws.service.impl;

import javax.management.RuntimeErrorException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adhithya.app.ws.UserRepository;
import com.adhithya.app.ws.io.entity.UserEntity;
import com.adhithya.app.ws.service.UserService;
import com.adhithya.app.ws.shared.Utils;
import com.adhithya.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired // implements functions of crud repository that user repository extends
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		
		if(userRepository.findByEmail(user.getEmail())!=null)
		{
			throw new RuntimeException("record exists already");
		}
		
		UserEntity userEntity = new UserEntity();
		// userdto and userentity should have same fields;
		BeanUtils.copyProperties(user, userEntity);
		
		String publicUserId = utils.generateUserId(30);
		
		//we create hardcode values since several properties were set to required
		userEntity.setEncryptedPassword("test");
		userEntity.setUserId(publicUserId);
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();
		
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		//the stored user entities are taken again to return back
		
		return returnValue;
	}

}
