/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.exceptions;

public interface ErrorMessages {

	
	/*
	 * JSON
	 */
	String ALREADY_REPORTED_JSON = "{\"msg\":\"This action was already reported by you.\"}";
	String INTERNAL_ERROR_JSON = "{\"msg\":\"Server error: ERR_MSG\"}";
	String USER_EXISTS_JSON = "{\"msg\":\"User already exists in Yumu.\"}"; 
	String USER_EMAIL_EXISTS_JSON = "{\"msg\":\"User email already exists in Yumu.\"}"; 

	String UNKNOWN_USER_JSON = "{\"msg\":\"No such user in Yumu.\"}"; 

	/*
	 * Errors
	 */
	String USER_SHOULD_BE_LOGGED_IN = "User should be logged in ";
	String USER_NOT_ALLOWED = "User not allowed to access this resource ";
	String USER_NOT_A_OWNER = "User is not an owner ";
	String UNKNOWN_USER = "User is unknown";
	String BAD_NOTIFICATION_CONTEXT = "Invalid or missing notification config ";
	String RESOURCE_NOT_FOUND = "Resource not found ";
	String RESOURCE_NOT_ACTIVE = "Resource not active ";
	String CANNOT_UPDATE_LOCATION = "Activity location cannot be changed";
	String BAD_SOCIAL_INFO = "Invalid social account info in user";
	String USER_EMAIL_EXISTS = "User email already exists in Yumu";
	String IMAGE_ERROR = "Store image failed: ";
	String BAD_AUTHENTICATION = "Invalid security context";
	
	/*
	 * Warnings
	 */
	




}
