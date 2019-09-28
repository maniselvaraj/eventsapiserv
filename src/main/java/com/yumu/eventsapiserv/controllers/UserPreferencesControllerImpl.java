/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.managers.UserPreferencesManager;
import com.yumu.eventsapiserv.pojos.user.preferences.Choice;
import com.yumu.eventsapiserv.pojos.user.preferences.Preferences;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;


@RestController
@RequestMapping(path = Api.BASE_VERSION +  "/users/{userId}")
public class UserPreferencesControllerImpl {
	
	@Autowired
	private UserPreferencesManager prefsManager;
	

	@RequestMapping(method=RequestMethod.GET, path="/preferences")
	public ResponseEntity<?> getUserPreferences(@PathVariable("userId") String userId){

		try {
			UserAuthenticationUtils.getAuthenticatedUser();
			Preferences preferences = prefsManager.getPreferencesByUserId(userId);
			if(preferences == null) {
				preferences = prefsManager.initializePreferencesForUser(userId);
			}
			if(preferences==null || preferences.getChoices()==null){
				return HttpResponseUtil.resource_not_found("Preferences for "+ userId);
			}
			return  new ResponseEntity<>(preferences.getChoices(), HttpStatus.OK);
		}
		catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT, path="/preferences")
	public ResponseEntity<?> updatePreferences(@PathVariable("userId") String userId, @RequestBody List<Choice> choices){
		
		try {
			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(userId);
			Preferences preferences = prefsManager.getPreferencesByUserId(userId);
			if(preferences == null) {
				preferences = prefsManager.initializePreferencesForUser(userId);
			}
			
			Preferences savedPreferences = prefsManager.updatePreferences(userId, preferences, choices);

			return new ResponseEntity<>(savedPreferences, HttpStatus.OK);
		}catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}


}
