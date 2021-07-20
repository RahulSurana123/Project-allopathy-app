package com.allopathy.management.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.allopathy.management.exception.CustomResponseStatusException;
import com.allopathy.management.exception.ErrorCodeEnum;
import com.allopathy.management.model.user.UserDetails;
import com.allopathy.management.repository.UserRepository;
import com.allopathy.management.security.model.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl {

	@Autowired
	UserRepository userRepository;
	
	public UserDetails getUserDetails(String username) {
		User user = getUser(username);
		return new UserDetails(user,user.getEnabled(),!user.getLocked());
	}

	private User getUser(String username) {
		Optional<User> user= userRepository.findById(username);
		if(user.isEmpty()) {
			throw new CustomResponseStatusException(HttpStatus.NOT_FOUND, ErrorCodeEnum.EX1999);
		}
		return user.get();
	}
	
}
