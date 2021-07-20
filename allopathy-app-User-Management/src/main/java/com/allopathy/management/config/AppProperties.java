package com.allopathy.management.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


import lombok.Data;

/**
 * Application specific custom properties
 * 
 * @author dprasad
 *
 */
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {

	/**
	 * the jwt related config
	 */
	private Jwt jwt;
	
}
