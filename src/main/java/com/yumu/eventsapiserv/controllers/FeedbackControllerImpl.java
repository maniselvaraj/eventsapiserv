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

import com.yumu.eventsapiserv.managers.FeedbackManager;
import com.yumu.eventsapiserv.pojos.common.Feedback;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;


@RestController
@RequestMapping(path= Api.BASE_VERSION + "/system/feedback")
public class FeedbackControllerImpl {

	@Autowired
	private FeedbackManager fbManager;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createFeedback(@Valid @RequestBody Feedback fb, BindingResult result){
		
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		
		/*
		 * Manual validation
		 */
		if(StringUtils.isBlank(fb.getName()) &&
				StringUtils.isBlank(fb.getOwner()) &&
				StringUtils.isBlank(fb.getPhone()) && 
				StringUtils.isBlank(fb.getEmail())){
			return HttpResponseUtil.getJsonMessage("Atleast one of name, email, phone, owner is required", 
					HttpStatus.BAD_REQUEST);
		}
		
		Feedback f = this.fbManager.create(fb);
		if(f!=null && StringUtils.isNotBlank(f.getId())){
			return HttpResponseUtil.getJsonMessage("Successfully saved feedback", HttpStatus.OK);
		} else {
			return HttpResponseUtil.getJsonMessage("Failed to save feedback", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAllFeedback(Pageable page){
		Page<Feedback> p = this.fbManager.getAll(page);
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
}
