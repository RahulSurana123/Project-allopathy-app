package com.allopathy.management.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.allopathy.management.sha2.SHA2PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BeanConfig {

	
	/**
	 * The password encoder to be used
	 * 
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SHA2PasswordEncoder();
	}
	
}
