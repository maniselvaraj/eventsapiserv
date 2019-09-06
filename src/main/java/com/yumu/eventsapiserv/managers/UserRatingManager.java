package com.yumu.eventsapiserv.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.common.UserRating;
import com.yumu.eventsapiserv.repositories.UserRatingRepository;


@Component
public class UserRatingManager {
	
	@Autowired
	private UserRatingRepository ratingRepo;
	
	public UserRating create(UserRating entity){
		return this.ratingRepo.save(entity);
	}
	
	public Page<UserRating> getAll(Pageable page){
		return this.ratingRepo.findAll(page);
	}

}
