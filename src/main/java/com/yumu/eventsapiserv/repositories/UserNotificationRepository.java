package com.yumu.eventsapiserv.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.yumu.eventsapiserv.pojos.notification.UserNotification;


public interface UserNotificationRepository extends MongoRepository<UserNotification, String> {

	Page<UserNotification> findByReceiverAndStatus(String id, String status, Pageable page);
	
	Page<UserNotification> findBySenderAndStatus(String id, String status, Pageable page);
	
	UserNotification findBySenderAndReceiverAndTypeAndStatus(String sender, String receiver, String type, String status);
	
	@Query(value="{$and: [{'sender': ?0},{'receiver': ?1},{'type': ?2},{'context.type': ?3},{'context.contextId': ?4}]}")	
	UserNotification findBySenderAndReceiverAndTypeAndContext(String sender, String receiver, 
			String notificationType, String contextType, String contextId);

	Long countByReceiverAndStatus(String id, String status);
}
