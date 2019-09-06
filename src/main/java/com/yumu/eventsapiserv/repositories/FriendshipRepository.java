package com.yumu.eventsapiserv.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.user.Friendship;

public interface FriendshipRepository extends MongoRepository<Friendship, String> {

	Friendship findByYourIdAndTheirId(String yourId, String theirId);

	List<Friendship> findByYourId(String yourId);
	
	//List<Friendship> findByYourIdAndStatus(String yourId, String status);
	
	Page<Friendship> findByYourIdAndStatus(String yourId, String status, Pageable page);
}
