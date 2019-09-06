package com.yumu.eventsapiserv.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.common.UserRating;


public interface UserRatingRepository extends MongoRepository<UserRating, String> {

}

