package com.yumu.eventsapiserv.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.common.Feedback;
import com.yumu.eventsapiserv.repositories.FeedbackRepository;


@Component
public class FeedbackManager {
	
	@Autowired
	private FeedbackRepository feedbackRepo;
	
	public Feedback create(Feedback entity){
		return this.feedbackRepo.save(entity);
	}
	
	public Page<Feedback> getAll(Pageable page){
		return this.feedbackRepo.findAll(page);
	}

}
