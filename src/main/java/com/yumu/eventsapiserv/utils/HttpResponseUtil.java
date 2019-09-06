package com.yumu.eventsapiserv.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpResponseUtil {

	public static ResponseEntity<?> resource_not_found(String id) {
		String msg = "{\"msg\": \"Resource " + id + " not found\"}";
		return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	}

	public static ResponseEntity<?> getJsonMessage(String _message, HttpStatus status) {
		String msg = "{\"msg\": \"" + _message + "\"}";
		return new ResponseEntity<>(msg, status);
	}

	public static MultiValueMap<String, String> getNoCacheHeader(){
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		headers.add("Pragma", "no-cache"); // HTTP 1.0.
		headers.add("Expires", "0"); 
		return headers;
	}

}

