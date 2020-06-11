package com.adhithya.app.ws.service.impl;

import javax.management.RuntimeErrorException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		/*
		 * We'll use runtime exception until we define our own custom exception
		 */
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("record exists already");
		}

		UserEntity userEntity = new UserEntity();
		// userdto and userentity should have same fields;
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateUserId(30);

		/*
		 * we use bcryptpassword encoder which was added as a bean to our
		 * MobileAppWsApplication java file
		 */
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);

		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();

		BeanUtils.copyProperties(storedUserDetails, returnValue);
		// the stored user entities are taken again to return back

		return returnValue;
	}

	/*
	 * This method is given to us by spring security when userservice class extended
	 * UserDetailsService. The load by username function is provided by spring security as well
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
