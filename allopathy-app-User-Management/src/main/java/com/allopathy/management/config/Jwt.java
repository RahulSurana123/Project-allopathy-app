/**
 * Copyright (c) 2019 Mavenir Systems
 */
package com.allopathy.management.config;

import lombok.Data;

/**
 * Application specific jwt properties
 * 
 * @author dprasad
 *
 */
@Data
public class Jwt {

	/**
	 * issuer of the jwt
	 */
	private String issuer;

	/**
	 * the secret key to encrypt the signature of the jwt
	 */
	private String secret;

	/**
	 * the validity in milliseconds of the access token
	 */
	private Long accessValidity;

	/**
	 * the client id which is set as audience in the jwt
	 */
	private String clientId;
}
