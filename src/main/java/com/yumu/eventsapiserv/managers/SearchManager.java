package com.yumu.eventsapiserv.managers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.activities.Activity;
import com.yumu.eventsapiserv.pojos.activities.Post;
import com.yumu.eventsapiserv.pojos.user.YumuUser;


@Component
public class SearchManager {


	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Activity> searchTopActivity(String search, int limit){

		String[] words = search.split(" ");

//		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);
//		Query query = TextQuery
//						.queryText(criteria)
//						.sortByScore()
//						.limit(limit);

		Query query = Query.query(Criteria.where("status").is("ACTIVE"))
				.addCriteria(TextCriteria.forDefaultLanguage().matchingAny(words))
				.limit(limit);

		return this.mongoTemplate.find(query, Activity.class);
	}

	public Page<Activity> searchActivity(String search, Pageable page){

		String[] words = search.split(" ");

		//		TextCriteria criteria = TextCriteria
		//				.forDefaultLanguage()
		//				.matchingAny(words);

		Query query = Query.query(Criteria.where("status").is("ACTIVE"))
				.with(page)
				.addCriteria(TextCriteria.forDefaultLanguage().matchingAny(words));

		List<Activity> activities = 
				this.mongoTemplate.find(query, Activity.class);
		long count = this.mongoTemplate.count(query, Activity.class);

		Page<Activity> activityDetailsPage = new PageImpl<Activity>(activities, 
				page, count);
		return activityDetailsPage;
	}

	public long getSearchActivityCount(String search){

		String[] words = search.split(" ");

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);

		Query query = TextQuery.queryText(criteria);

		return this.mongoTemplate.count(query, Activity.class);
	}


	public List<Post> searchPost(String search, Pageable page){

		String[] words = search.split(" ");

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);

		Query query = TextQuery
				.queryText(criteria)
				.sortByScore()
				.with(page);

		return this.mongoTemplate.find(query, Post.class);

	}

	public long getSearchPostCount(String search){

		String[] words = search.split(" ");

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);

		Query query = TextQuery.queryText(criteria);

		return this.mongoTemplate.count(query, Post.class);
	}

	public List<YumuUser> searchTopUser(String search, int limit){

		String[] words = search.split(" ");

//		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);
//		Query query = TextQuery
//				.queryText(criteria)
//				.sortByScore()
//				.limit(limit);
		
		Query query = Query.query(Criteria.where("status").is("ENABLED"))
				.addCriteria(TextCriteria.forDefaultLanguage().matchingAny(words))
				.limit(limit);

		return this.mongoTemplate.find(query, YumuUser.class);

	}

	public Page<YumuUser> searchUser(String search, Pageable page){

		String[] words = search.split(" ");

		//TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);
		//		Query query = TextQuery
		//				.queryText(criteria)
		//				.sortByScore()
		//				.with(page);
		//return this.userRepo.findAllBy(criteria, page);

		Query query = Query.query(Criteria.where("status").is("ENABLED"))
				.with(page)
				.addCriteria(TextCriteria.forDefaultLanguage().matchingAny(words));

		List<YumuUser> users = 
				this.mongoTemplate.find(query, YumuUser.class);
		long count = this.mongoTemplate.count(query, YumuUser.class);

		Page<YumuUser> userDetails = new PageImpl<YumuUser>(users, 
				page, count);
		return userDetails;
		
	}

	public long getYumuUserCount(String search){

		String[] words = search.split(" ");

		TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);

		Query query = TextQuery.queryText(criteria);

		return this.mongoTemplate.count(query, YumuUser.class);
	}

}
