/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.managers.*;
import com.yumu.eventsapiserv.pojos.activities.*;
import com.yumu.eventsapiserv.pojos.common.PageImplWithMeta;
import com.yumu.eventsapiserv.repositories.*;
import com.yumu.eventsapiserv.utils.*;




/**
 * All activities associated with an user
 * @author mani
 *
 */

@RestController
@RequestMapping(path = Api.BASE_VERSION + "/users/")
public class UserActivitiesControllerImpl {


	@Autowired
	private ActivityRepository activitiesRepo;

	@Autowired
	private ActivityManager activityManager;

	@Autowired
	private ActivityUserLinkRepository activityUserLinkRepo;
	
	@Autowired
	private ResponseMetaDataManager responseMetaMgr;

	@Autowired
	private AppLogManager dblogger;
	
	private final static Logger logger = LogManager.getLogger(UserActivitiesControllerImpl.class);

	
	/*
	 * get all activities that is connected to an user
	 * uri: /vx/users/{user_id}/activities
	 * 
	 * TODO: Warning: Caller in X-YUMU-SECURITY-CONTEXT could be different from {id} in uri.
	 * Need role based authorization for this in phase 2
	 * If {id} and X-YUMU-SECURITY-CONTEXT does not match, then role based access check needs to be done.
	 */

	/*
	 * This api will grow to be complicated.
	 * TODO:
	 * 1. Sort by date, location, membership, pinned, sponsored, 
	 * 
	 * What should this return?
	 * 1. Activities associated with an user i.e., activities that an user is a member of, pinned, sponsored
	 */

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e) {
		// do something. e.g. customize error response
		//e.printStackTrace();
		Map<String, String> msg = new HashMap<>();
		msg.put("msg", e.getMessage());
		return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*
	 * 1. First get the list from the mapping collection activityUserLink based on location and sorted by time.
	 * 2. Use the list of ids to fetch from activity repo.
	 */
	@RequestMapping(path="/{userId}/activities",method=RequestMethod.GET)
	public ResponseEntity<?> getUsersActivitiesByLocation(
			@RequestHeader(value="X-YUMU-LOCATION", required=false) String location,
			@PathVariable("userId") String userId, 
			@RequestParam(name="user_type", required=false) UserRelation.UserType userType,
			@RequestParam(name="action_type", required=false) ActivityUserAction.Action actionType,
			Pageable pageable) {

		if(userType != null || actionType != null ){
			return getUserActivitiesByType(userType, actionType, userId, pageable);
		}

		/*
		 * TODO: over ride the pagination
		 */
		//https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-seven-pagination/

		try {
			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(userId);

			UserActivitiesControllerImpl.logger.debug("GET MY ACTIVITIES for " + userId + " and location " + location);
			Circle circle = null;

			if(StringUtils.isNotBlank(location)){
				String[] elements = location.split(",");
				if(elements.length==3 
						&& StringUtils.isNotBlank(elements[0])
						&& StringUtils.isNotBlank(elements[1])
						&& StringUtils.isNotBlank(elements[2])) {
					double x = Double.valueOf(elements[0]);
					double y = Double.valueOf(elements[1]);
					//double radius = Double.valueOf(elements[2]);
					
					double radius = ActivityUtil.getMinimumRadius(elements[2]);
					
					circle = new Circle( x, y, radius);
				}
			} 
			
			/*
			 * construct the page
			 */

			Pageable p = new PageRequest(pageable.getPageNumber(), 
		            pageable.getPageSize(), 
		            new Sort(Sort.Direction.DESC, "updatedAt"));

			Page<ActivityUserLink> links = null;
			if(circle !=null){
				links = this.activityUserLinkRepo.findByYumuUserIdAndUserRelationStatusAndLocationPointWithin(
						userId, UserRelation.Status.ACTIVE, circle,  p /* pageable */ );
			} else {
				links = this.activityUserLinkRepo.findByYumuUserIdAndUserRelationStatus(
						userId, UserRelation.Status.ACTIVE,  p /* pageable */ );
			}

			//List<String> activityIds = new LinkedList<>();
			//List<Activity> myActivities = new LinkedList<>();
			List<ActivityDetail> activityDetails = new LinkedList<>();
			
			links.forEach(link -> {
				link.setLocation(null); //we dont need to send location here
				//userRelationMap.put(link.getActivityId(), link);
				//activityIds.add( link.getActivityId());
				Activity act = this.activitiesRepo.findByIdAndStatus(link.getActivityId(), Activity.Status.ACTIVE);
				if(act!=null){
					ActivityDetail detail = this.activityManager.decorateActivityWithDetail(act);
					activityDetails.add(detail);
				}
			});

			
			//System.out.println("First act id " + activityIds.get(0));
			//List<Activity> myActivities = this.activitiesRepo.findByIdInAndStatus(activityIds, 
				//	Activity.Status.ACTIVE /* , pageable.getSort() */);
			
			//String sort_msg = "Link act id: " + links.getContent().get(0).getActivityId();
			//sort_msg += " ;act id " + myActivities.get(0).getId();
			//UserActivitiesControllerImpl.logger.debug(sort_msg);
			
			//System.out.println("First acts " + myActivities.get(0).getId() );
			//List<ActivityDetail> activityDetails = new LinkedList<>();
			/*
			 * Populate the relation between the user and activity
			 */
			//myActivities.forEach(act -> {
				//ActivityDetail detail = this.activityManager.decorateActivityWithDetail(act);
				//activityDetails.add(detail);
			//});

//			System.out.println("First acts det " + activityDetails.get(0).getId() );
//			System.out.println("********************");
			
			Map<String, String> meta = this.responseMetaMgr.getResponseMetadata(userId);

			Page<ActivityDetail> activityDetailsPage = new PageImplWithMeta<ActivityDetail>(activityDetails, 
					pageable, 
					links.getTotalElements(),
					meta);
			
			String msg = "MY ACTITIVITIES: user: " + userId + " top act: " + activityDetails.get(0).getName();
			this.dblogger.info(msg);
			this.logger.debug(msg);
			
			MultiValueMap<String, String> headers = HttpResponseUtil.getNoCacheHeader();

			return new ResponseEntity<>(activityDetailsPage, headers, HttpStatus.OK);
		}
		catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}

	private ResponseEntity<?> getUserActivitiesByType(UserRelation.UserType userType, 
			ActivityUserAction.Action actionType, String userId, Pageable page) {

		Page<ActivityUserLink> links = null;

		if( actionType == null) {
			links = this.activityUserLinkRepo.findByYumuUserIdAndUserRelationUserTypeAndUserRelationStatus(userId, 
					userType, UserRelation.Status.ACTIVE, page);
		} else if(userType == null ){
			links = this.activityUserLinkRepo.findByYumuUserIdAndUserActionsActionAndUserRelationStatus(userId, 
					actionType, UserRelation.Status.ACTIVE, page);
		} else 
		{
			links = this.activityUserLinkRepo.findByYumuUserIdAndUserRelationUserTypeAndUserActionsActionAndUserRelationStatus(userId, 
					userType, actionType, UserRelation.Status.ACTIVE, page);
		}

		List<String> activityIds = new ArrayList<>();
		links.forEach(link -> {
			activityIds.add(link.getActivityId());
		});

		List<Activity> myActivities = this.activitiesRepo.findByIdInAndStatus(activityIds, 
				Activity.Status.ACTIVE, page.getSort());

		List<ActivityDetail> activityDetails = new ArrayList<>();
		/*
		 * Populate the relation between the user and activity
		 */
		myActivities.forEach(act -> {
			ActivityDetail detail = this.activityManager.decorateActivityWithDetail(act);
			activityDetails.add(detail);
		});

		Page<ActivityDetail> activityDetailsPage = new PageImpl<ActivityDetail>(activityDetails, 
				page, 
				links.getTotalElements());


		MultiValueMap<String, String> headers = HttpResponseUtil.getNoCacheHeader();

		return new ResponseEntity<>(activityDetailsPage, headers, HttpStatus.OK);

	}


}
