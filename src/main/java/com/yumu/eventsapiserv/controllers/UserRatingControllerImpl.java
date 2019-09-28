package com.yumu.eventsapiserv.controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.managers.UserRatingManager;
import com.yumu.eventsapiserv.pojos.common.UserRating;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;

@RestController
@RequestMapping(path= Api.BASE_VERSION + "/system/userrating")
public class UserRatingControllerImpl {

	@Autowired
	private UserRatingManager manager;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createRating(@Valid @RequestBody UserRating rating, BindingResult result){

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		/*
		 * Manual validation
		 */
		if(StringUtils.isBlank(rating.getName()) &&
				StringUtils.isBlank(rating.getOwner()) &&
				StringUtils.isBlank(rating.getPhone()) && 
				StringUtils.isBlank(rating.getEmail())){
			return HttpResponseUtil.getJsonMessage("Atleast one of name, email, phone, owner is required", 
					HttpStatus.BAD_REQUEST);
		}
		
		UserRating f = this.manager.create(rating);
		if(f!=null && StringUtils.isNotBlank(f.getId())){
			return HttpResponseUtil.getJsonMessage("Successfully saved rating", HttpStatus.OK);
		} else {
			return HttpResponseUtil.getJsonMessage("Failed to save rating", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAllRating(Pageable page){
		Page<UserRating> p = this.manager.getAll(page);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	
}
