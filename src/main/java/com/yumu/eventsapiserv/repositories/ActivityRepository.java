package com.yumu.eventsapiserv.repositories;


import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yumu.eventsapiserv.pojos.activities.Activity;




public interface ActivityRepository extends MongoRepository<Activity, String> {

	void deleteById(String id);
	
	Page<Activity> findByIdIn(Collection<String> ids, Pageable page);

	Activity findByIdAndStatus(String id, Activity.Status status);
	
	Page<Activity> findByIdInAndStatus(Collection<String> ids, Activity.Status status, Pageable page);
	
	List<Activity> findByIdInAndStatus(Collection<String> ids, Activity.Status status, Sort sort);

	List<Activity> findByIdInAndStatus(Collection<String> ids, Activity.Status status);

	Page<Activity> findByStatus(Activity.Status status, Pageable page);
	
	Page<Activity> findByStatusAndLocationPointWithin(Activity.Status status, Circle circle, Pageable page);

	/*
	 * I am pretty surprised that below crap worked.
	 * Spring mongo constructed the query from the method signature. Awesome!
	 */
	Page<Activity> findByStatusAndLocationPointWithinAndMembersYumuUserIdNot(Activity.Status status, Circle circle, String userId, Pageable page);

	Page<Activity> findByStatusAndMembersYumuUserIdNot(Activity.Status status, String userId, Pageable page);
	
	
	/*
	 * Search api
	 */
	Page<Activity> findAllBy(TextCriteria criteria, Pageable page);
	
	//Page<Activity> findAllBy(Query q,  Pageable page);

	
}
