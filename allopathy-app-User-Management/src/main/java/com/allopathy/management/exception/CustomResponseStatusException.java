package com.allopathy.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class CustomResponseStatusException extends ResponseStatusException {

	private final String errorCode;

	public CustomResponseStatusException(HttpStatus status,
			ErrorCodeEnum errorCodeEnum) {
		super(status, errorCodeEnum.getReasonString());
		this.errorCode = errorCodeEnum.getErrorCode();
	}

	public CustomResponseStatusException(HttpStatus status,
			String message) {
		super(status,message);
		errorCode="";
		
	}

	public String getErrorCode() {
		return errorCode;
	}
}