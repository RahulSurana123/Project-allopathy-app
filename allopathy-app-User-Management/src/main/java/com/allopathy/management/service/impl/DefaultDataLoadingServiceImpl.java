package com.allopathy.management.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.allopathy.management.repository.UserRepository;
import com.allopathy.management.security.model.User;
import com.allopathy.management.util.LoadingUtility;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Service
@Slf4j
public class DefaultDataLoadingServiceImpl {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoadingUtility loadingUtility;
	
	/**
	 * Password encoder.
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void deafaultDataLoading() {
		deafaultDataLoading("mainUser.json");
	}
	
	public void deafaultDataLoading(String filename) {
		User defaultUser = null;
		User defaultDBUser = null;
		try {
			defaultUser = loadingUtility.readUsers(filename);
			if (defaultUser != null) {
				Optional<User> userOpt = userRepository
						.findById(defaultUser.getId());
				if (userOpt.isPresent()) {
					defaultDBUser = userOpt.get();
				}
				if (defaultDBUser == null) {
					// Calling save method to save account details.
					defaultUser.setPassword(
							passwordEncoder.encode(defaultUser.getPassword()));
					defaultDBUser = userRepository.save(defaultUser);
					log.info("Default user added with id "
							+ defaultDBUser.getId());
				} else {
					log.info("Default user already present with id : "
							+ defaultDBUser.getId());
				}
			} else {
				log.warn("Error loading default user.");
			}
		} catch (NoSuchElementException obj) {
			log.error("No user found.");
		}
	}
	
}
