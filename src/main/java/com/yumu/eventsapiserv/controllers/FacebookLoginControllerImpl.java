/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = Api.BASE_VERSION + "/users/facebook")
public class FacebookLoginControllerImpl {

	@Autowired
	private AppLogManager logger;
	
	@Autowired
	private UserRepository userRepo;
	
//	@Autowired
//	private UserManager userManager;
	
//	@Autowired
//	private SocialFriendshipRepository socialFriendRepo;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e) {
		// do something. e.g. customize error response
		e.printStackTrace();
		Map<String, String> msg = new HashMap<>();
		msg.put("msg", e.getMessage());
		return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*
	 * Does the facebook user exist in Yumu already?
	 */
	@RequestMapping(path="/{facebookid}", method=RequestMethod.GET)
	public ResponseEntity<?> doesUserExistAlreadyInYumu(@PathVariable("facebookid") String facebookUserId) {

		Map<String, String> userInfo = new HashMap<>();
		userInfo.put("facebook_id", facebookUserId);


		YumuUser user = userRepo.findByFacebookUserId(facebookUserId);
		if(user!=null){
			userInfo.put("exists_in_yumu", "true");
			userInfo.put("yumu_user_id", user.getId());
		} else {
			userInfo.put("exists_in_yumu", "false");
		}
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}
//
//	/*
//	 * register/persist a new facebook user into Yumu
//	 */
//	@RequestMapping(method=RequestMethod.POST)
//	public ResponseEntity<?> create(@Valid @RequestBody YumuUser user, BindingResult result ){
//		if(result.hasErrors()){
//			List<FieldError>errors = result.getFieldErrors();
//			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//		}
//
//
//		/*
//		 * Check if user is already present. 
//		 * TODO: Use unique index later https://docs.mongodb.com/v3.0/tutorial/create-a-unique-index/
//		 *
//		YumuUser fbUser = userRepo.findByFacebookUserId(user.getSocialInfo().get(0).getUserId());
//		if(fbUser!=null){
//			return new ResponseEntity<>(ErrorMessages.USER_EXISTS_JSON, HttpStatus.ALREADY_REPORTED);
//		} 
//		 */
//		/*
//		 * check the email
//		 *
//		YumuUser fbUserByEmail = userRepo.findByFacebookEmail(UserUtil.getFacebookIdFromUser(user));
//		if(fbUserByEmail!=null){
//			return new ResponseEntity<>(ErrorMessages.USER_EMAIL_EXISTS_JSON, HttpStatus.ALREADY_REPORTED);
//		}
//		 */
//		
//		/*
//		 * If images section is empty, and facebook image url is present, then set it as default image
//		 */
//		if(user.getImages()==null || user.getImages().isEmpty()) {
//			String facebookProfileImg = UserUtil.getFacebookProfileImageFromUser(user);
//			if(StringUtils.isNotBlank(facebookProfileImg)){
//				if(user.getImages()==null){
//					user.setImages(new ArrayList<Image>());
//				}
//				Image image = new Image();
//				image.setPrimary(Boolean.TRUE);
//				image.setType(Image.Type.PROFILE);
//				image.setSrc(facebookProfileImg);
//				user.getImages().add(image);
//			}
//		}
//		
//		/*
//		 * TODO: if name is fetched from facebook, copy it to name_info object's screen name
//		 */
//		if(user.getSocialInfo().get(0) !=null && StringUtils.isNotBlank( user.getSocialInfo().get(0).getName())){
//			if(user.getNameInfo()==null){
//				user.setNameInfo(new NameInfo());
//			}
//			user.getNameInfo().setScreenName(user.getSocialInfo().get(0).getName());
//		}
//		
//
//		user.setCreatedAt(new DateTime(DateTimeZone.UTC));
//		user.setStatus(YumuUser.Status.ENABLED);
//
//		YumuUser yuser = null;
//		try{
//			yuser = userRepo.save(user);
//		}
//		catch(DuplicateKeyException e){
//			//System.out.println(			e.getMostSpecificCause());
//			//e.printStackTrace();
//			return new ResponseEntity<>(ErrorMessages.USER_EXISTS_JSON, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		this.logger.info("successfully created user " + yuser.getId());
//		return new ResponseEntity<>(yuser, HttpStatus.OK);
//	}
//
//	
//	@RequestMapping(path="/friends", method=RequestMethod.POST)
//	public ResponseEntity<?> uploadFacebookFriends( @Valid @RequestBody SocialFriends friends ){
//		
//		try {
//			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
//			UserDetails principal = (UserDetails) auth.getPrincipal();
//			this.userManager.checkIfExists(principal.getUsername());
//			
//			if(!friends.getYumuUserId().equals(principal.getUsername())){
//				return HttpResponseUtil.getJsonMessage("Mismatched users", HttpStatus.BAD_REQUEST);
//			}
//			
//			friends.getFriends().forEach(friend -> {
//				SocialFriendship friendship = new SocialFriendship();
//				friendship.setSignInProvider(SignInProvider.FACEBOOK);
//				friendship.setYourId(principal.getUsername());
//				friendship.setTheirSocialId(friend);
//				friendship.setCreatedAt(new DateTime(DateTimeZone.UTC));
//				friendship.setStatus(SocialFriendship.Status.OPEN);
//				
//				this.socialFriendRepo.save(friendship);
//			});
//
//			return HttpResponseUtil.getJsonMessage("Persisted friends count " + friends.getFriends().size(), 
//					HttpStatus.OK);
//		}
//		catch(ApiAccessException e){
//			return e.getHttpResponse();
//		}
//		
//	}
//

}
