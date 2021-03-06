package com.adhithya.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.adhithya.app.ws.io.entity.UserEntity;
import com.adhithya.app.ws.io.repositories.UserRepository;
import com.adhithya.app.ws.security.SecurityConstants;
import com.adhithya.app.ws.service.UserService;
import com.adhithya.app.ws.shared.Utils;
import com.adhithya.app.ws.shared.dto.UserDto;
import com.adhithya.app.ws.ui.model.request.UserDetailsRequestModel;

import io.jsonwebtoken.Jwts;

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

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	/*
	 * This method is given to us by spring security when userservice class extended
	 * UserDetailsService. The load by username function is provided by spring
	 * security as well
	 * 
	 * This method is used to load user information from the database using
	 * username, In our application the unique identifier is email
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) {
			// exception provided by spring framework
			throw new UsernameNotFoundException(email);
		}

		// user implements user details so we can return user instead of userdetails
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	/*
	 * return the user information of the userid that is given
	 */
	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}

		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		UserDto returnUser = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}

		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userRepository.save(userEntity);

		BeanUtils.copyProperties(userEntity, returnUser);
		return returnUser;
	}

	@Override
	public boolean validateUser(String token, String currentUserId) {
		if (token != null) {

			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
			String user = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token)
					.getBody().getSubject();

			if (user != null) {
				UserEntity userEntity = userRepository.findByEmail(user);
				if (userEntity.getUserId().equals(currentUserId)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean deleteUser(String userId) {
		UserDto returnUser = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		boolean isdeleteDone = false;
		if (userEntity != null) {
			userRepository.delete(userEntity);
			isdeleteDone = true;
		}

		return isdeleteDone;
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<UserDto>();

		if (page > 0) {
			page = page - 1;
		}

		Pageable pageableRequest = PageRequest.of(page, limit);
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }
		
		return returnValue;
	}

}
