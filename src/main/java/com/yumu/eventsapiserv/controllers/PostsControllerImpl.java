/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.controllers;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.managers.*;
import com.yumu.eventsapiserv.pojos.activities.*;
import com.yumu.eventsapiserv.pojos.activities.PostUserAction.Action;
import com.yumu.eventsapiserv.pojos.common.PageImplWithMeta;
import com.yumu.eventsapiserv.repositories.*;
import com.yumu.eventsapiserv.utils.*;

@RestController
@RequestMapping(path = Api.BASE_VERSION + "/core/posts")
public class PostsControllerImpl {



	@Autowired
	private PostUserActionRepository postActionRepo;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private PostsManager postMananger;

	/*
	 * create one activity.
	 * TBD: Not sure if this will be used. The other option is
	 * POST /vx/core/activities/id/posts
	 */
//	@RequestMapping(method=RequestMethod.POST)
//	public ResponseEntity<?> create(@RequestBody Post post){
//
//		try {
//			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
//			UserDetails principal = (UserDetails) auth.getPrincipal();
//
//			/*
//			 * Check if activity is active
//			 */
//			this.activityManager.checkIfActive(post.getActivityId());
//			
//			/*
//			 * set the owner 
//			 */
//			YumuUser owner = userRepo.findOne(principal.getUsername());
//			post.setOwner(owner.getId());
//			post.setCreatedAt(new DateTime(DateTimeZone.UTC));
//			post.setUpdatedAt(new DateTime(DateTimeZone.UTC));
//			Post savedPost = postsRepo.save(post);
//			return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
//		}
//		catch(ApiAccessException e){
//			return e.getHttpResponse();
//		}
//
//	}

	@RequestMapping(path="/{postId}/like", method=RequestMethod.POST)
	public ResponseEntity<?> like(@PathVariable("postId") String postId){
		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			PostUserAction postAction = postActionRepo.findByPostIdAndYumuUserIdAndAction(postId, 
					principal.getUsername(), Action.LIKE.name());
			if(postAction==null){
				postAction = new PostUserAction();
				postAction.setPostId(postId);
				postAction.setYumuUserId(principal.getUsername());
				postAction.setAction(Action.LIKE);
				//TODO: check date
				postAction.setCreatedAt(new DateTime(DateTimeZone.UTC));
				postActionRepo.save(postAction);

			} else {
				return new ResponseEntity<>(ErrorMessages.ALREADY_REPORTED_JSON, HttpStatus.ALREADY_REPORTED);
			}

			Query query = new Query(Criteria.where("id").is(postId));
			Update update = new Update();
			update.inc("metrics.likes", 1);
			update.set("updatedAt", new DateTime(DateTimeZone.UTC));
			mongoTemplate.findAndModify(query, update, Post.class);
			
			return HttpResponseUtil.getJsonMessage("User " + principal.getUsername() 
						+" liked post " + postId, HttpStatus.OK);
			//return new ResponseEntity<>(null, HttpStatus.OK);
		}
		catch(ApiAccessException e){
			return e.getHttpResponse();
		}

	}

	@RequestMapping(path="/{postId}/report", method=RequestMethod.POST)
	public ResponseEntity<?> report(@PathVariable("postId") String postId){
		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			PostUserAction postAction = postActionRepo.findByPostIdAndYumuUserIdAndAction(postId, 
					principal.getUsername(), Action.REPORT_ABUSE.name());
			if(postAction==null){
				postAction = new PostUserAction();
				postAction.setPostId(postId);
				postAction.setYumuUserId(principal.getUsername());
				postAction.setAction(Action.REPORT_ABUSE);
				//TODO: check date
				postAction.setCreatedAt(new DateTime(DateTimeZone.UTC));
				postActionRepo.save(postAction);

			} else {
				return new ResponseEntity<>(ErrorMessages.ALREADY_REPORTED_JSON, HttpStatus.ALREADY_REPORTED);
			}
			//TODO: Need to do something about the reported abuse
			
			Query query = new Query(Criteria.where("id").is(postId));
			Update update = new Update().inc("metrics.reported", 1).set("updatedAt", new DateTime(DateTimeZone.UTC));
			Post updatedPost = mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(Boolean.TRUE), Post.class);
			
			return HttpResponseUtil.getJsonMessage("User " + principal.getUsername() 
			+" reported post " + postId, HttpStatus.OK);

			//return new ResponseEntity<>(updatedPost, HttpStatus.OK);
		}
		catch(ApiAccessException e){
			return e.getHttpResponse();
		}

	}

	
	@RequestMapping(path="/{postId}", method=RequestMethod.POST)
	public ResponseEntity<?> getPostById(@PathVariable("postId") String postId){
		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			Post post = this.postMananger.getPostById(postId);
			return new ResponseEntity<>(post, HttpStatus.OK);
		}
		catch(ApiAccessException e){
			return e.getHttpResponse();
		}

	}

	
}
