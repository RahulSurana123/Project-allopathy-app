/**
 * Copyright (c) 2019 Mavenir Systems
 */
package com.allopathy.management.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The error response to be sent for an exception
 * 
 * @author dprasad
 *
 */
@Data
@AllArgsConstructor
public class ExceptionResponse {

	/**
	 * The date and time of the error
	 */
	private Date timestamp;

	/**
	 * The error message
	 */
	private String message;

	/**
	 * The additional details of the error
	 */
	private String details;
	
	/**
	 * The trace id of the request
	 */
	private String traceId;
	
	/**
	 * The span id of the request
	 */
	private String spanId;
	
	/**
	 * Error code
	 */
	private String errorCode;

}
