package com.yumu.eventsapiserv.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.common.Feedback;


public interface FeedbackRepository extends MongoRepository<Feedback, String>  {

}
