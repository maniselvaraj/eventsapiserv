package com.yumu.eventsapiserv.controllers;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.managers.UserManager;
import com.yumu.eventsapiserv.pojos.user.RegistrationInfo;
import com.yumu.eventsapiserv.pojos.user.RegistrationInfo.Status;
import com.yumu.eventsapiserv.repositories.RegistrationRepository;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;


@RestController
@RequestMapping(path = Api.BASE_VERSION + "/users")
public class RegistrationControllerImpl {
	
	private final static Logger logger = LogManager.getLogger(RegistrationControllerImpl.class);

	
	@Autowired
	private RegistrationRepository regRepo;
	
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(method=RequestMethod.GET, path="/registrations")
	public ResponseEntity<?> getRegistrations(Pageable page) {
		Page<RegistrationInfo> pages = this.regRepo.findAll(page);
		return new ResponseEntity<>(pages, HttpStatus.OK);
	}
	
	/*
	 * Register an user's device
	 * This registration device used for notifications
	 */
	@RequestMapping(method=RequestMethod.POST, path="/login")
	public ResponseEntity<?> login(@Valid @RequestBody  RegistrationInfo regInfo) {
		
		/*
		 * Check if the user is valid
		 */
		if(this.userManager.findById(regInfo.getYumuUserId())==null){
			return new ResponseEntity<>(ErrorMessages.UNKNOWN_USER_JSON, HttpStatus.BAD_REQUEST);
		}
		
		this.logger.debug("LOGIN user:"+regInfo.getYumuUserId() + " token:"+regInfo.getToken());
		/*
		 * check if there already a login from the same devvice
		 */
		RegistrationInfo savedRegInfo = this.regRepo.findByYumuUserIdAndToken(regInfo.getYumuUserId(), regInfo.getToken());

		if( savedRegInfo == null) {
			regInfo.setCreatedAt(new DateTime(DateTimeZone.UTC));
			regInfo.setUpdatedAt(regInfo.getCreatedAt());
			regInfo.setStatus(Status.LOGGED_IN);
			this.regRepo.save(regInfo);
			return HttpResponseUtil.getJsonMessage("User "+ regInfo.getYumuUserId() + " successfully logged in!",
					HttpStatus.OK);
		}  else {
			//there is an existing login. So update it
			savedRegInfo.setUpdatedAt(new DateTime(DateTimeZone.UTC));
			savedRegInfo.setStatus(Status.LOGGED_IN);
			this.regRepo.save(savedRegInfo);
			return HttpResponseUtil.getJsonMessage("User "+ regInfo.getYumuUserId() + " successfully logged in again!",
					HttpStatus.OK);
		}
		
	}

	/*
	 * User logged out or deleted the app
	 */
	@RequestMapping(method=RequestMethod.POST, path="/logout")
	public ResponseEntity<?> logout(@RequestBody RegistrationInfo regInfo) {
		
		Map<String, String> msgs = new HashMap<>();
		msgs.put("yumu_user_id", regInfo.getYumuUserId());
		msgs.put("token", regInfo.getToken());
		
		this.logger.debug("LOGOUT user:"+regInfo.getYumuUserId() + " token:"+regInfo.getToken());

		RegistrationInfo savedReg = this.regRepo.findByYumuUserIdAndToken(regInfo.getYumuUserId(), regInfo.getToken());
		if(savedReg!=null){
			savedReg.setStatus(Status.LOGGED_OUT);
			savedReg.setUpdatedAt(new DateTime(DateTimeZone.UTC));
			this.regRepo.save(savedReg);
			msgs.put("status", "LOGGEDOUT");
		} else {
			msgs.put("status", "REGISTRATION_NOT_FOUND");
			return new ResponseEntity<>(msgs, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(msgs, HttpStatus.OK);
	}

	
}
