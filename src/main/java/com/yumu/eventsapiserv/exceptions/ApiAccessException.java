/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiAccessException extends Exception {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String errorMsg;
	private final HttpStatus httpSatus;

	public ApiAccessException(String message){
		super(message);
		this.errorMsg = message;
		this.httpSatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public ApiAccessException(String message, HttpStatus status){
		super(message);
		this.errorMsg = message;
		this.httpSatus = status;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public HttpStatus getHttpSatus() {
		return httpSatus;
	}
	
	public ResponseEntity<?> getHttpResponse(){
		Map<String, String> response = new HashMap<>();
		response.put("msg", this.getErrorMsg());
		return new ResponseEntity<>(response, this.getHttpSatus());
	}

}
