package com.yumu.eventsapiserv.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.user.SocialFriendship;


public interface SocialFriendshipRepository extends MongoRepository<SocialFriendship, String> {

}
