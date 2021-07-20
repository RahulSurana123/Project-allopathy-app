package com.allopathy.management.security.model;

import java.util.Date;
import java.util.List;

import com.allopathy.management.constant.ClassIdConstants;

import lombok.Data;

@Data
public class User{

	String id;
	
	String username;
	
	Date expiryDate;

	String version;
	
	/**
	 * Map of all applicable resources and grants provided for them
	 */
	private List<String> privilages;
	
	/**
	 * encrypted login password of the user
	 */
	private String password;

	/**
	 * Verification Code.
	 */
	private String verificationCode;

	/**
	 * update time of the user
	 */
	private Date updatedAt;

	/**
	 * is the user locked
	 */
	private Boolean locked;

	/**
	 * creation time of the user
	 */
	private Date createdAt;

	/**
	 * is the user enabled/active
	 */
	private Boolean enabled;
	
	private String classId = ClassIdConstants.USER_DOC_ID;
	
}
