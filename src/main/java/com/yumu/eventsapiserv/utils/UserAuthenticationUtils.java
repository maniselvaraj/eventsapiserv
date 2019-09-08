/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.utils;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.UserRepository;

@Component
public class UserAuthenticationUtils {
	
	private static UserRepository userRepo;
	
	@Autowired
	private UserRepository UR;
	
	@PostConstruct
	public void init(){
		UserAuthenticationUtils.userRepo = UR;
	}

	public static Authentication getAuthenticatedUser() throws ApiAccessException {
		/*
		 * check if user is logged in. This is not really a solid check.
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new ApiAccessException(ErrorMessages.USER_SHOULD_BE_LOGGED_IN, 
					HttpStatus.UNAUTHORIZED);
		}
		
		UserDetails principal = (UserDetails) auth.getPrincipal();
		Optional<YumuUser> user = userRepo.findById(principal.getUsername());
		if(user.isEmpty()){
			throw new ApiAccessException(ErrorMessages.UNKNOWN_USER, 
					HttpStatus.UNAUTHORIZED);
		}
		return auth;
	}
	
	public static String getAuthenticatedUserId() throws ApiAccessException {
		/*
		 * check if user is logged in. This is not really a solid check.
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new ApiAccessException(ErrorMessages.USER_SHOULD_BE_LOGGED_IN, 
					HttpStatus.UNAUTHORIZED);
		}
		UserDetails principal = (UserDetails) auth.getPrincipal();
		if (principal == null || StringUtils.isEmpty(principal.getUsername())) {
			throw new ApiAccessException(ErrorMessages.BAD_AUTHENTICATION, 
					HttpStatus.UNAUTHORIZED);
		}
		return principal.getUsername();
	}

	public static String getAuthenticatedUserIdNoException() {
		/*
		 * check if user is logged in. This is not really a solid check.
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		UserDetails principal = (UserDetails) auth.getPrincipal();
		if (principal == null || StringUtils.isEmpty(principal.getUsername())) {
			return null;
		}
		return principal.getUsername();
	}
	
	public static Authentication getAuthenticatedUserNoException() {
		/*
		 * check if user is logged in. This is not really a solid check.
		 */
		return  SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static Authentication isUserAuthenticatedAndAllowed(String resourceOwnerId) throws ApiAccessException {
		/*
		 * check if user is logged in. This is not really a solid check.
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new ApiAccessException(ErrorMessages.USER_SHOULD_BE_LOGGED_IN, 
					HttpStatus.UNAUTHORIZED);
		}
		UserDetails principal = (UserDetails) auth.getPrincipal();
		/*
		 * TODO: bad way to check if caller is allowed to access
		 */
		if(!resourceOwnerId.equals(principal.getUsername())){
			throw new ApiAccessException(ErrorMessages.USER_NOT_ALLOWED, 
					HttpStatus.UNAUTHORIZED);
		}
		return auth;
	}
	
	
	
	
}
