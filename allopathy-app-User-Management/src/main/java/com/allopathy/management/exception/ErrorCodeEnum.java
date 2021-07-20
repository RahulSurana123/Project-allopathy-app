package com.allopathy.management.exception;

/**
 *
 * @author Abhishek Kothari
 */
public enum ErrorCodeEnum {

	//Account .. Also contains general errors like patch, permission etc validation errors
	VA1999("VA1999", "General error."),
	VA2000("VA2000", "User Not Found."),
		
	ER2001("EX2001","Access Denied For User"),
	
	EX1999("EX1999","Exception occured"), 
	EX2000("EX2000","Query Timeout Error"), 
	EX2001("EX2001","Cas Mismatch Execption"), 
	EX2002("EX2002", "Document Already Stored");	
	private String errorCode;
	private String reasonString;

	private ErrorCodeEnum(final String errorCode, String reasonString) {
		this.errorCode = errorCode;
		this.reasonString = reasonString;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getReasonString() {
		return reasonString;
	}

}