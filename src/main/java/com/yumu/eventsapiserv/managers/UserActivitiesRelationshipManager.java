package com.yumu.eventsapiserv.managers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.pojos.activities.Activity;
import com.yumu.eventsapiserv.pojos.activities.ActivityDetail;
import com.yumu.eventsapiserv.pojos.activities.ActivityUserAction;
import com.yumu.eventsapiserv.pojos.activities.ActivityUserAction.Action;
import com.yumu.eventsapiserv.pojos.activities.Member;

import com.yumu.eventsapiserv.pojos.activities.ActivityUserLink;
import com.yumu.eventsapiserv.pojos.activities.UserRelation;
import com.yumu.eventsapiserv.pojos.activities.UserRelation.Status;
import com.yumu.eventsapiserv.pojos.activities.UserRelation.UserType;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.ActivityRepository;
import com.yumu.eventsapiserv.repositories.ActivityUserLinkRepository;
import com.yumu.eventsapiserv.utils.ActivityUserActionUtil;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;




/**
 * Class to manager the relation between an activity and user.
 * @author mani
 *
 */

@Component
public class UserActivitiesRelationshipManager {

	/*
	 * stores the relation between activity and user. Kind of SQL mapping table.
	 */
	@Autowired
	private ActivityUserLinkRepository activityUserLinkRepo;

	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ActivityRepository activityRepo;
	
	@Autowired
	private ActivityManager activityManager;
	
	@Autowired
	private MongoTemplate mongoTemplate;


	public void createLink(Activity activity, String yumuUserId, UserType type, Status status ) {
		try {
			ActivityUserLink link = new ActivityUserLink();
			link.setActivityId(activity.getId());
			link.setYumuUserId(yumuUserId);
			
			link.setCreatedAt(new DateTime(DateTimeZone.UTC));
			link.setUpdatedAt(new DateTime(DateTimeZone.UTC));
			
			UserRelation relation = new UserRelation();
			relation.setUserType(type);
			relation.setStatus(status);
			relation.setCreatedAt(activity.getTimeInfo().getCreatedAt()); //TODO
			link.setUserRelation(relation);
			link.setLocation(activity.getLocation());

			activityUserLinkRepo.save(link);
		}
		catch(Exception e){
			//TODO: needs exception handling
			e.printStackTrace();
		}
	}

	/*
	 * check if the user is already related to the activity
	 */
	public ActivityUserLink checkLink(String activityId, String username) {

		return activityUserLinkRepo.findByActivityIdAndYumuUserId(activityId, username);
	}


	public Page<YumuUser> getActiveMembers(String activityId, Pageable page) {
		//Get only approved members
		List<ActivityUserLink> links = activityUserLinkRepo.
				findByActivityIdAndRelationStatus(activityId, UserRelation.Status.ACTIVE.name());
		List<String> yuserIds = new ArrayList<>();
		links.forEach( (e) -> yuserIds.add(e.getYumuUserId()) );
		return  this.userManager.findByIdIn(yuserIds, page);
		
	}

	public Page<YumuUser> getPendingMembers(String activityId, Pageable page) {
		//Get only pending members
		String[] statuses = {UserRelation.Status.PENDING_OWNER_APPROVAL.name(),
				UserRelation.Status.PENDING_USER_ACCEPTANCE.name()};
		List<ActivityUserLink> links = activityUserLinkRepo.
				findByActivityIdAndRelationStatuses(activityId, statuses);
		List<String> yuserIds = new ArrayList<>();
		links.forEach( (e) -> yuserIds.add(e.getYumuUserId()) );
		return  this.userManager.findByIdIn(yuserIds, page);
	}
	public List<YumuUser> getAllPendingMembers(String activityId) {
		//Get only pending members (all)
		String[] statuses = {UserRelation.Status.PENDING_OWNER_APPROVAL.name(),
				UserRelation.Status.PENDING_USER_ACCEPTANCE.name()};
		List<ActivityUserLink> links = activityUserLinkRepo.
				findByActivityIdAndRelationStatuses(activityId, statuses);
		List<String> yuserIds = new ArrayList<>();
		links.forEach( (e) -> yuserIds.add(e.getYumuUserId()) );
		return  this.userManager.findByIdIn(yuserIds);
	}

	
	/*
	 * Gets all the activity user links for a particular activity
	 */
	public List<ActivityUserLink> getActivityUserLinks(String activityId) {
		return activityUserLinkRepo.findByActivityId(activityId);
	}
	
	/*
	 * Get the single link between an user and activity
	 */
	public ActivityUserLink getActivityUserLink(String activityId, String userId) {
		return activityUserLinkRepo.findByActivityIdAndYumuUserId(activityId, userId);
	}
	
	
	/*
	 * returns a map of userid and activity-user-link
	 */
	public Map<String, ActivityUserLink> getActivityUserLinksMap(String activityId, boolean bNoLocation) {
		
		List<ActivityUserLink> links = activityUserLinkRepo.findByActivityId(activityId);
		
		Map<String, ActivityUserLink> map =  new LinkedHashMap<>();
		
		links.forEach(l -> {
			if(bNoLocation){
				l.setLocation(null);
			}
			map.put(l.getYumuUserId(), l);
		});
		
		return map;
		
	}

	
	@Deprecated
	public void approveMembership(String activityId, String ownerId, String userId, UserType type) throws ApiAccessException{
		
		Activity act = activityRepo.findById(activityId).get();
		/*
		 * check if allowed owner
		 */
		this.activityManager.checkIfOwner(ownerId, act);
		
		/*
		 * check if userId is valid
		 */
		this.userManager.checkIfExists(userId);
		
		ActivityUserLink link = checkLink(activityId, userId);
		if(link == null) {
			createLink(act, userId, type, Status.ACTIVE);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.ACTIVE);
			//TODO: check if its upsert
			activityUserLinkRepo.save(link);
		}
		/*
		 * Increment member count
		 */
		//this.activityManager.updateMemberCount(activityId, 1);
		this.activityManager.addMemberToActivity(activityId, userId, Member.Type.MEMBER);

	}

	public void approveMembership(Activity act, String userId, UserType type) throws ApiAccessException {
		
		/*
		 * check if userId is valid
		 */
		this.userManager.checkIfExists(userId);
		
		ActivityUserLink link = checkLink(act.getId(), userId);
		if(link == null) {
			createLink(act, userId, type, Status.ACTIVE);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.ACTIVE);
			//TODO: check if its upsert
			activityUserLinkRepo.save(link);
		}
		
		/*
		 * Increment member count
		 */
		//this.activityManager.updateMemberCount(act.getId(), 1);
		this.activityManager.addMemberToActivity(act.getId(), userId, Member.Type.MEMBER);
	}
	
	public void acceptMembership(String activityId, String userId) {
		ActivityUserLink link = checkLink(activityId, userId);
		if(link == null) {
			Activity act = activityRepo.findById(activityId).get();
			createLink(act, userId, UserType.MEMBER, Status.ACTIVE);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.ACTIVE);
			activityUserLinkRepo.save(link);
		}
		/*
		 * Increment member count
		 */
		//this.activityManager.updateMemberCount(activityId, 1);
		this.activityManager.addMemberToActivity(activityId, userId, Member.Type.MEMBER);
	}


	/*
	 * Someone sent a membership request and owner is denying
	 */
	@Deprecated
	public void denyMembership(String activityId, String ownerId, String userId, UserType type) throws ApiAccessException {
		Activity act = activityRepo.findById(activityId).get();
		
		/*
		 * check if allowed owner
		 */
		this.activityManager.checkIfOwner(ownerId, act);
		
		ActivityUserLink link = checkLink(activityId, userId);
		if(link == null) {
			createLink(act, userId, type, Status.DENIED);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.DENIED);
			activityUserLinkRepo.save(link);
		}
		
	}
	public void denyMembership(Activity act, String userId, UserType type) throws ApiAccessException {
		
		ActivityUserLink link = checkLink(act.getId(), userId);
		if(link == null) {
			createLink(act, userId, type, Status.DENIED);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.DENIED);
			activityUserLinkRepo.save(link);
		}
		
	}
	/*
	 * Someone sent  a membership request and user is declining
	 */
	public void declineMembership(String activityId, String userId) {
		ActivityUserLink link = checkLink(activityId, userId);
		if(link == null) {
			Activity act = activityRepo.findById(activityId).get();
			createLink(act, userId, UserType.MEMBER, Status.DECLINED);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.DECLINED);
			activityUserLinkRepo.save(link);
		}
		
	}

	public void ignoreMembershipByOwner(String activityId, String ownerId, String userId, UserType type) throws ApiAccessException {
		Activity act = activityRepo.findById(activityId).get();
		
		/*
		 * check if allowed owner
		 */
//		List<YumuUser> owners = act.getOwners();
//		boolean bFoundOwner = false;
//		for(YumuUser user: owners){
//			if(user.getId().equals(ownerId)) {
//				bFoundOwner = true;
//				break;
//			}
//		}
//		if(!act.getOwners().contains(ownerId)) {
//			throw new ApiAccessException(ErrorMessages.USER_NOT_A_OWNER, HttpStatus.FORBIDDEN);
//		}
//		
		/*
		 * check if allowed owner
		 */
		this.activityManager.checkIfOwner(ownerId, act);
		
		ActivityUserLink link = checkLink(activityId, userId);
		if(link == null) {
			createLink(act, userId, type, Status.IGNORED);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.IGNORED);
			activityUserLinkRepo.save(link);
		}
		
	}

	public void ignoreMembershipByUser(String activityId, String userId) {
		ActivityUserLink link = checkLink(activityId, userId);
		if(link == null) {
			Activity act = activityRepo.findById(activityId).get();
			createLink(act, userId, UserType.MEMBER, Status.IGNORED);
		} else {
			//update the link
			link.getUserRelation().setStatus(Status.IGNORED);
			activityUserLinkRepo.save(link);
		}
		
	}

	public void updateRelation(ActivityUserLink link) {
		this.activityUserLinkRepo.save(link);
	}
	
	public ResponseEntity<?> upsertActivityUserAction(String activityId, String userId, 
			Action action, ActivityUserAction.Status status){
		
		boolean alreadyReported = true;
		ActivityUserLink link = checkLink(activityId, userId);
		if(link!=null){
			
			if(link.getUserRelation().getStatus()==UserRelation.Status.BLOCKED){
				return HttpResponseUtil.getJsonMessage("Blocked user: " + userId, 
						HttpStatus.UNAUTHORIZED);
			}
			
			alreadyReported = link.getUserActions() != null ? 
				link.getUserActions().stream().anyMatch(a -> a.getAction() == action) 
				: false;
				
			if(!alreadyReported){
				//Update the record
				ActivityUserAction like = new ActivityUserAction();
				like.setAction(action /*ActivityUserAction.Action.LIKE*/);
				like.setStatus(status /*ActivityUserAction.Status.ENABLED*/);
				like.setCreatedAt(new DateTime(DateTimeZone.UTC));
				link.getUserActions().add(like);
				updateRelation(link);
			}
		}
		else{

			link = ActivityUserActionUtil.createActivityUserActionLink(activityId, 
					userId, 
					action, 
					status);
			
			updateRelation(link);
			
		} 
			
		if(alreadyReported){
			return new ResponseEntity<>(ErrorMessages.ALREADY_REPORTED_JSON, HttpStatus.ALREADY_REPORTED);
		}

		Query query = new Query(Criteria.where("id").is(activityId));
		String metric = StringUtils.lowerCase("metrics." + action.name());
		Update update = new Update().inc(metric, 1);
		Activity activity = mongoTemplate.findAndModify(query, 
														update, 
														FindAndModifyOptions.options().returnNew(Boolean.TRUE),
														Activity.class);
		
		ActivityDetail detail = activityManager.decorateActivityWithDetail(activity);
		
		return new ResponseEntity<>(detail, HttpStatus.OK);
	}


	public void blockMembership(Activity act, String userId, UserType type) throws ApiAccessException{
		
		/*
		 * check if userId is valid
		 */
		this.userManager.checkIfExists(userId);
		
		ActivityUserLink link = checkLink(act.getId(), userId);
		if(link!=null){
			//update the link
			link.getUserRelation().setStatus(Status.BLOCKED);
			link.getUserRelation().setUpdatedAt(new DateTime(DateTimeZone.UTC));
			//TODO: check if its upsert
			activityUserLinkRepo.save(link);
			//this.activityManager.updateMemberCount(act.getId(), -1);
			this.activityManager.removeMemberFromActivity(act.getId(), userId, Member.Type.MEMBER);
		} else {
			throw new ApiAccessException("User/activity not related: " +act.getId()+"/"+userId, 
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * Method to get an user's activity statistics i.e.,
	 * number of activities created by user
	 * number of activities where user is a member
	 */
	/*
	 *  db.activityUserLink.aggregate([{$match:{"yumuUserId": "58308a87c92e56a680394217"}}, {$group:{_id:{userRelation:'$userRelation.userType'}, count:{$sum:1}}}])
	 */
	/*
	 * This metric is computed every time. Rather, it can be stored in the user record.
	 */
	public List<ActivityCount> getUserActivityMetrics(String userId){
		
		Aggregation agg = newAggregation(
				match(Criteria.where("yumuUserId").is(userId)),
				group("userRelation.userType").count().as("total")
			);
	
		AggregationResults<ActivityCount> groupResults
		= mongoTemplate.aggregate(agg, ActivityUserLink.class, ActivityCount.class);

		return groupResults.getMappedResults();
		
//		List<ActivityCount> result = groupResults.getMappedResults();
//		
//		for(ActivityCount c: result){
//			System.out.println(c.getId() + "=" + c.getTotal());
//		}
		
	}

	public void setActivityLinkStatus(String activityId,
			com.yumu.eventsapiserv.pojos.activities.UserRelation.Status status) {
		Query query = new Query(Criteria.where("activityId").is(activityId));
		Update update = new Update().set("userRelation.status", status);
		this.mongoTemplate.updateMulti(query, update, ActivityUserLink.class);
	}



}
