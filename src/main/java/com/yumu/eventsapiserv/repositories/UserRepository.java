package com.yumu.eventsapiserv.repositories;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.pojos.user.YumuUser.Status;


public interface UserRepository  extends MongoRepository<YumuUser, String> {

	Optional<YumuUser> findById(String id);
	
	Page<YumuUser> findByIdIn(Collection<String> ids, Pageable page);
	List<YumuUser> findByIdIn(Collection<String> ids);

	YumuUser findByEmail(String email);
	
	YumuUser findByEmailAndStatus(String email, Status status);
	
	YumuUser findByIdAndStatus(String id, Status status);
	
	/*
	 * Custom query to determine if facebook user is present or not
	 * http://docs.spring.io/spring-data/mongodb/docs/1.2.x/reference/html/mongo.repositories.html
	 */
	@Query(value="{$and: [{'socialInfo.signInProvider': 'FACEBOOK'},{'socialInfo.userId': ?0}]}")
	YumuUser findByFacebookUserId(String facebookUserId);
	
	@Query(value="{$and: [{'socialInfo.signInProvider': 'FACEBOOK'},{'socialInfo.email': ?0}]}")
	YumuUser findByFacebookEmail(String email);
	
	/*
	 * search
	 */
	Page<YumuUser> findAllBy(TextCriteria criteria, Pageable page);
	
	/*
	 * get all users except for some ids
	 */
	Page<YumuUser> findByIdNotIn( Collection<String> userIds, Pageable page);


}
