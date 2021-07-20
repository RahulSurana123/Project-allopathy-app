/*
 * Copyright (c) 2020. Mavenir Systems. 
 */

package com.allopathy.management.sha2;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SHA2PasswordEncoder implements PasswordEncoder {

	/**
	 * Encode the raw password. Generally, a good encoding algorithm applies a
	 * SHA-1 or greater hash combined with an 8-byte or greater randomly
	 * generated salt.
	 *
	 * @param rawPassword
     *            the raw password to encode
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		return DigestUtils.sha256Hex(rawPassword.toString());
	}

	/**
	 * Verify the encoded password obtained from storage matches the submitted
	 * raw password after it too is encoded. Returns true if the passwords
	 * match, false if they do not. The stored password itself is never decoded.
	 *
	 * @param rawPassword
	 *            the raw password to encode and match
	 * @param encodedPassword
	 *            the encoded password from storage to compare with
	 * @return true if the raw password, after encoding, matches the encoded
	 *         password from storage
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword
				.equals(DigestUtils.sha256Hex(rawPassword.toString()));
	}
}
