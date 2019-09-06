package com.yumu.eventsapiserv.repositories;


import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.activities.PostUserAction;


public interface PostUserActionRepository extends MongoRepository<PostUserAction, String>{

	List<PostUserAction> findByPostIdAndYumuUserId(String postId, String yumuUserId);
	
	PostUserAction findByPostIdAndYumuUserIdAndAction(String postId, String yumuUserId, String action);
	
	
	List<PostUserAction> findByPostIdIn(Collection<String> ids);
	
}
