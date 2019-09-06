/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.yumu.eventsapiserv.pojos.activities.ActivityUserAction.Action;
import com.yumu.eventsapiserv.pojos.activities.ActivityUserLink;
import com.yumu.eventsapiserv.pojos.activities.UserRelation;
import com.yumu.eventsapiserv.pojos.activities.UserRelation.Status;
import com.yumu.eventsapiserv.pojos.activities.UserRelation.UserType;



/**
 * Repository for storing the relation between an activity and an user
 * Relationship or link can be of type: owner, or user. 
 * Possible choices are owner, user, root, guest. No use for root and guest for now.
 * @author mani
 *
 */
public interface ActivityUserLinkRepository extends MongoRepository<ActivityUserLink, String> {

	
	/*
	 * Get all links associated to an user
	 */
	Page<ActivityUserLink> findByYumuUserId(String yumuUserId, Pageable pageable);

	/*
	 * Get all links associated to an activity
	 */
	List<ActivityUserLink> findByActivityId(String activityId);
	
	//TODO: broken
	//List<ActivityUserLink> findByActivityIdAndStatus(String activityId, String status);
	
	ActivityUserLink findByActivityIdAndYumuUserId(String activityId, String username);

	@Query(value="{$and: [{'activityId': ?0},{'userRelation.status': ?1}]}")
	List<ActivityUserLink> findByActivityIdAndRelationStatus(String activityId, String name);
	
	@Query(value="{$and: [{'activityId': ?0},{'userRelation.status': {$in:?1}}]}")
	List<ActivityUserLink> findByActivityIdAndRelationStatuses(String activityId, String[] name);
	
	Page<ActivityUserLink> findByYumuUserIdAndUserRelationStatusAndLocationPointWithin(String userId, 
			UserRelation.Status status, Circle circle, Pageable page);

	Page<ActivityUserLink> findByYumuUserIdAndUserRelationStatus(String userId, 
			UserRelation.Status status, Pageable page);
	
	Page<ActivityUserLink> findByYumuUserIdAndUserRelationUserTypeAndUserRelationStatus(String userId, 
			UserRelation.UserType type, UserRelation.Status status, Pageable page);

	Page<ActivityUserLink> findByYumuUserIdAndUserActionsActionAndUserRelationStatus(String userId, Action actionType,
			Status active, Pageable page);

	Page<ActivityUserLink> findByYumuUserIdAndUserRelationUserTypeAndUserActionsActionAndUserRelationStatus(
			String userId, UserType userType, Action actionType, Status active, Pageable page);
	
}
