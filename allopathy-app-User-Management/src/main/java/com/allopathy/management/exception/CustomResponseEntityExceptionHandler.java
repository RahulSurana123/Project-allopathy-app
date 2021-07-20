/**
 * Copyright (c) 2019 Mavenir Systems
 */
package com.allopathy.management.exception;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.couchbase.client.core.error.CasMismatchException;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
@Slf4j
public class CustomResponseEntityExceptionHandler
		extends ResponseEntityExceptionHandler {

	@Autowired
	Tracer tracer;

	/**
	 * The catch all exception handler
	 * 
	 * @param ex
	 * @param request
	 * @return the error response to be sent
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex,
			WebRequest request) {
		log.error("Internal server error.", ex);
		String traceId = tracer.currentSpan().context().traceIdString();
		String spanId = tracer.currentSpan().context().spanIdString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				ex.getMessage(), request.getDescription(false), traceId, spanId,
				ErrorCodeEnum.EX1999.getErrorCode());
		return new ResponseEntity<Object>(exceptionResponse,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * The Optimistic Locking Failure Exception handler
	 * 
	 * @param ex
	 * @param request
	 * @return the error response to be sent
	 */
	@ExceptionHandler(CasMismatchException.class)
	public final ResponseEntity<Object> handleCasMismatchException(
			CasMismatchException ex, WebRequest request) {
		log.warn("Trying to save stale data in to the database.", ex);
		return handleCustomResponseStatusException(
				new CustomResponseStatusException(
						HttpStatus.PRECONDITION_FAILED, ErrorCodeEnum.EX2001),
				request);
	}

	/**
	 * The Access denied exception handler
	 * 
	 * @param ex
	 * @param request
	 * @return the error response to be sent
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<Object> handleAccessDeniedExceptions(
			AccessDeniedException ex, WebRequest request) {
		log.error("Access is denied.", ex);
		String traceId = tracer.currentSpan().context().traceIdString();
		String spanId = tracer.currentSpan().context().spanIdString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				ex.getMessage(), request.getDescription(false), traceId, spanId,
				ErrorCodeEnum.ER2001.getErrorCode());
		return new ResponseEntity<Object>(exceptionResponse,
				HttpStatus.FORBIDDEN);
	}



	@ExceptionHandler({ ResponseStatusException.class })
	public ResponseEntity<Object> handleResponseStatusException(
			ResponseStatusException excp, WebRequest request) {
		String traceId = tracer.currentSpan().context().traceIdString();
		String spanId = tracer.currentSpan().context().spanIdString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				excp.getReason(), request.getDescription(false), traceId,
				spanId, ErrorCodeEnum.VA1999.getErrorCode());
		return new ResponseEntity<Object>(exceptionResponse, excp.getStatus());
	}


	/**
	 * The query runtime exception handler
	 * 
	 * @param ex
	 * @param request
	 * @return the error response to be sent
	 */
	@ExceptionHandler(QueryTimeoutException.class)
	ResponseEntity<Object> handleQueryTimeoutException(QueryTimeoutException ex,
			WebRequest request) {
		Throwable exc = ex.getCause();
		log.error("DB query timeout exception caused by : ", exc);
		return handleCustomResponseStatusException(
				new CustomResponseStatusException(
						HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeEnum.EX2000),
				request);
	}

	@ExceptionHandler({ CustomResponseStatusException.class })
	public ResponseEntity<Object> handleCustomResponseStatusException(
			CustomResponseStatusException excp, WebRequest request) {
		String traceId = tracer.currentSpan().context().traceIdString();
		String spanId = tracer.currentSpan().context().spanIdString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				excp.getReason(), request.getDescription(false), traceId,
				spanId, excp.getErrorCode());
		return new ResponseEntity<>(exceptionResponse, excp.getStatus());
	}


	
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(
			ConstraintViolationException excp, WebRequest request) {
		String traceId = tracer.currentSpan().context().traceIdString();
		String spanId = tracer.currentSpan().context().spanIdString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				excp.getMessage(), request.getDescription(false), traceId,
				spanId,ErrorCodeEnum.VA1999.getErrorCode());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.error("Missing parameter {}", ex.getParameterName(), ex);
		String traceId = tracer.currentSpan().context().traceIdString();
		String spanId = tracer.currentSpan().context().spanIdString();
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
				"Missing required parameter " + ex.getParameterName(),
				ex.getMessage(), traceId, spanId,
				ErrorCodeEnum.VA1999.getErrorCode());
		return new ResponseEntity<>(exceptionResponse,
				HttpStatus.BAD_REQUEST);
	}

}
