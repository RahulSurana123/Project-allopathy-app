
package com.allopathy.management.security.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizationVM {

	/**
	 * username
	 */
	private String username;
	
	/**
	 * servicename
	 */
	private String serviceName;

	/**
	 * The privileges granted to user
	 */
	private Map<String, List<String>> privileges;

	@Override
	public String toString() {
		return username;
	}

}
