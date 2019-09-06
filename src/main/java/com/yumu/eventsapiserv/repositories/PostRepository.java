/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.yumu.eventsapiserv.pojos.activities.Post;



/**
 * Mongodb Spring repository for yumu posts
 * @author mani
 *
 */
public interface PostRepository extends MongoRepository<Post, String> {
	
	/*
	 * Get all posts under an activity
	 */
	//Slice<Post> findByActivityId(String activityId, Pageable pageable);

	Page<Post> findByActivityId(String activityId, Pageable pageable);
	
	
	Page<Post> findByActivityIdAndStatus(String activityId, String status, Pageable pageable);
	
	Page<Post> findByActivityIdAndStatusOrderByCreatedAtDesc(String activityId, String status, Pageable pageable);
	
	@Query(value="{$and: [{'activityId': ?0},{'metrics.reported': {'$gt':?1}}]}")
	Page<Post> findByActivityIdAndAbusivePosts(String activityId, int count, Pageable pageable);

	@Query(value="{$and: [{'activityId': ?0},{'metrics.reported': {'$gt':?1}}, {'status':?2}]}")
	Page<Post> findByActivityIdAndAbusivePostsAndActive(String activityId, int count, String status, Pageable pageable);

	
}
