package com.yumu.eventsapiserv.controllers;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.managers.*;
import com.yumu.eventsapiserv.pojos.activities.*;
import com.yumu.eventsapiserv.pojos.activities.UserRelation.UserType;
import com.yumu.eventsapiserv.pojos.user.Invitation;
import com.yumu.eventsapiserv.repositories.*;
import com.yumu.eventsapiserv.utils.*;


/**
 * Controller that takes care of actions performed on an activity by a single user/owner
 * @author mani
 *
 */

@RestController
@RequestMapping(path = Api.BASE_VERSION +  "/core/activities/{activityId}")
public class ActivityUserActionControllerImpl {


	//private final static Logger logger = LogManager.getLogger(ActivityUserActionControllerImpl.class);

	/*
	 * Stores the core activity data
	 */
	@Autowired
	private ActivityRepository activitiesRepo;

	//@Autowired
	//private ActivityUserActionRepository activityUserActionRepo;
	
	@Autowired
	private UserManager userManager;
	
	
	@Autowired
	private ActivityManager activityManager;

//	@Autowired
//	private MongoTemplate mongoTemplate;

	/*
	 * stores the relation between activity and user. Kind of SQL mapping table.
	 */
	@Autowired
	private UserActivitiesRelationshipManager userActivityRelationManager;

	@Autowired
	private UserNotificationsManager userNotificationsManager;

	/*
	 * Like an activity
	 */
	@RequestMapping(path = "/like", method = RequestMethod.POST)
	public ResponseEntity<?> likeActivity(@PathVariable("activityId") String activityId) {

		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			return userActivityRelationManager.upsertActivityUserAction(activityId, 
					principal.getUsername(), ActivityUserAction.Action.LIKES, ActivityUserAction.Status.ENABLED);

		}
		catch(ApiAccessException ae) {
			return ae.getHttpResponse();
		}
	}


	/*
	 * Pin an activity
	 */
	@RequestMapping(path = "/pin", method = RequestMethod.POST)
	public ResponseEntity<?> pinActivity(@PathVariable("activityId") String activityId) {

		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			userActivityRelationManager.upsertActivityUserAction(activityId, 
					principal.getUsername(), ActivityUserAction.Action.PINS, ActivityUserAction.Status.ENABLED);
			
			return HttpResponseUtil.getJsonMessage("User " + principal.getUsername() + " successfully pinned activity " + activityId, 
					HttpStatus.OK);

		}
		catch(ApiAccessException ae) {
			return ae.getHttpResponse();
		}
	}
	
/*	@RequestMapping(path = "/pin", method = RequestMethod.POST)
	public ResponseEntity<?> pinActivityOld(@PathVariable("activityId") String activityId) {

		//String msg = null;
		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			ActivityUserLink link = userActivityRelationManager.checkLink(activityId, principal.getUsername());


			if(link==null){

				
				 * check if activity is public. If not, then created link with status = pending
				 
				Activity activity = activitiesRepo.findOne(activityId);

				userActivityRelationManager.createLink(activity, 
						principal.getUsername(), 
						UserRelation.UserType.FOLLOWER,
						UserRelation.Status.PINNED);
				//msg = "{\"msg\":\"User has been added as member\"}";

			}
			else {
				//TODO: check the hard coded message
				return new ResponseEntity<>("{\"msg\":\"User is already associated with this activity\"}", HttpStatus.ALREADY_REPORTED);
			}

			Query query = new Query(Criteria.where("id").is(activityId));
			Update update = new Update().inc("metrics.pins", 1);
			Activity activity = mongoTemplate.findAndModify(query, update, Activity.class);
			return new ResponseEntity<>(activity, HttpStatus.OK);

		}
		catch(ApiAccessException ae) {
			return ae.getHttpResponse();
		}

	}
*/
	/*
	 * Join an activity.
	 * User wants to join an activity. If its public, add them immediately.
	 * If the activity is private, create pending link and send notification to owner
	 */
	@RequestMapping(path = "/join", method = RequestMethod.POST)
	public ResponseEntity<?> joinActivity(@PathVariable("activityId") String activityId) {

		String msg = null;
		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			//Find if this user is already related to this activity

			ActivityUserLink link = userActivityRelationManager.checkLink(activityId, principal.getUsername());

			if(link==null){

				/*
				 * check if activity is public. If not, then created link with status = pending
				 */
				Optional<Activity> oActivity = activitiesRepo.findById(activityId);
				if(oActivity.isEmpty()){
					throw new ApiAccessException("Unable to find activity "+ activityId, HttpStatus.NOT_FOUND);
				}
				Activity activity = oActivity.get();

				if(activity.getAccessType() == Activity.AccessType.PUBLIC) {
					userActivityRelationManager.createLink(activity, 
							principal.getUsername(), 
							UserRelation.UserType.MEMBER,
							UserRelation.Status.ACTIVE);
					msg = "{\"msg\":\"User has been added as member\"}";
					
					//Query query = new Query(Criteria.where("id").is(activityId));
					//Update update = new Update().inc("metrics.members", 1);
					//mongoTemplate.findAndModify(query, update, Activity.class);
//					Query query = new Query(Criteria.where("id").is(activityId));
//					Member member = new Member();
//					member.setYumuUserId(principal.getUsername());
//					member.setType(Member.Type.MEMBER);
//					Update update = new Update().addToSet("members", member).inc("metrics.members", 1);
//					mongoTemplate.findAndModify(query, update, Activity.class);
					this.activityManager.addMemberToActivity(activityId, principal.getUsername(), Member.Type.MEMBER);

					return HttpResponseUtil.getJsonMessage("Successfully became a member of activity " + activityId, HttpStatus.OK);
					
				} else {
					//Not a public activity. So create a membership request and 
					//send a notification to owner
					userActivityRelationManager.createLink(activity, 
							principal.getUsername(), 
							UserRelation.UserType.MEMBER,
							UserRelation.Status.PENDING_OWNER_APPROVAL);
					userNotificationsManager.sendMembershipRequestToActivityOwners(activity, 
							principal.getUsername());
					msg = "{\"msg\":\"Membership request sent\"}";
				}

			}
			else {
				//TODO: check the hard coded message
				return new ResponseEntity<>("{\"msg\":\"User is already associated with this activity\"}", HttpStatus.ALREADY_REPORTED);
			}
			
			return HttpResponseUtil.getJsonMessage("Successfully sent membership request to activity " + activityId, HttpStatus.OK);
		}
		catch(ApiAccessException ae) {
			return ae.getHttpResponse();
		}
	}



	/*
	 * Report an activity
	 */
	@RequestMapping(path = "/report", method = RequestMethod.POST)
	public ResponseEntity<?> reportActivity(@PathVariable("activityId") String activityId) {

		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			//TODO: this should trigger a notice to admin
			userActivityRelationManager.upsertActivityUserAction(activityId, 
					principal.getUsername(), ActivityUserAction.Action.REPORTED, ActivityUserAction.Status.ENABLED);

			return HttpResponseUtil.getJsonMessage("User " + principal.getUsername() + " reported activity " + activityId, 
					HttpStatus.OK);

		}
		catch(ApiAccessException ae) {
			return ae.getHttpResponse();
		}
	}
	
//	@RequestMapping(path = "/report", method = RequestMethod.POST)
//	public ResponseEntity<?> reportActivity(@PathVariable("activityId") String activityId) {
//
//		try {
//
//			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
//			UserDetails principal = (UserDetails) auth.getPrincipal();
//
//			ActivityUserAction action = activityUserActionRepo.
//					findByActivityIdAndYumuUserIdAndAction(activityId, principal.getUsername(), Action.REPORT_ABUSE.name());
//
//			if(action == null){
//				//create a new action entry
//				action = ActivityUserActionUtil.createActivityUserAction(activityId, 
//						principal.getUsername(), 
//						ActivityUserAction.Status.ENABLED, //TODO: do you need status flag?
//						ActivityUserAction.Action.REPORT_ABUSE);
//				activityUserActionRepo.save(action);
//			} else {
//				//Activity activity = activitiesRepo.findOne(activityId);
//				return new ResponseEntity<>(ErrorMessages.ALREADY_REPORTED_JSON, HttpStatus.ALREADY_REPORTED);
//			}
//
//			//TODO: need to something about the abuse
//			return new ResponseEntity<>(null, HttpStatus.OK);
//
//		}
//		catch(ApiAccessException ae) {
//			return ae.getHttpResponse();
//		}
//	}


	/*
	 * Invite to an activity.
	 * User (can be a non-owner) can invite an user for an activity.
	 * This will send notification to activity owners.
	 * Use cases:
	 * 1. owner can invite
	 * 2. user can invite. invited member can accept and then owner has to approve.
	 */

	@RequestMapping(path = "/invite", method = RequestMethod.POST)
	public ResponseEntity<?> inviteToActivity(@PathVariable("activityId") String activityId, 
			@RequestBody Invitation invite) {

		String msg = null;
		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			/*
			 * validate the caller
			 */
			this.userManager.checkIfExists(invite.getTheirId());
			
			//Find if this user is already related to this activity

			ActivityUserLink link = userActivityRelationManager.checkLink(activityId, 
					invite.getTheirId());

			if(link==null){

				/*
				 * check if activity is public. If not, then created link with status = pending
				 */
				Optional<Activity> oActivity = activitiesRepo.findById(activityId);
				if(oActivity.isEmpty()) {
					throw new ApiAccessException("Unable to find activity "+ activityId, HttpStatus.NOT_FOUND);
				}
				Activity activity = oActivity.get();
				
				if(activity.getOwners().contains(principal.getUsername())){
					/*
					 * Adding members rather than sending invitation if caller is owner
					 */
					userActivityRelationManager.approveMembership(activity, invite.getTheirId(), UserType.MEMBER);
					
					userNotificationsManager.sendMembershipAdditionToUser(activity, 
							principal.getUsername(),
							invite.getTheirId());
				}
				else {
					userActivityRelationManager.createLink(activity, 
							invite.getTheirId(), 
							UserRelation.UserType.MEMBER,
							UserRelation.Status.PENDING_USER_ACCEPTANCE);
				
					userNotificationsManager.sendMembershipInvitationToUser(activity, 
							principal.getUsername(),
							invite.getTheirId());
					/*
					 * if caller is owner, then no need to send approval request to owner
					 */
					if(!activity.getOwners().contains(principal.getUsername())){
						userNotificationsManager.sendMembershipRequestToActivityOwners(activity, 
								invite.getTheirId());

					}
				}

			}
			else {
				//TODO: check the hard coded message
				return new ResponseEntity<>("{\"msg\":\"User is already associated with this activity\"}", HttpStatus.ALREADY_REPORTED);
			}

//			Query query = new Query(Criteria.where("id").is(activityId));
//			Update update = new Update().inc("metrics.members", 1);
//			mongoTemplate.findAndModify(query, update, Activity.class);
			
			return HttpResponseUtil.getJsonMessage("Successfully invited user " + invite.getTheirId() + " to activity " + activityId, HttpStatus.OK); 
		}
		catch(ApiAccessException ae) {
			return ae.getHttpResponse();
		}
	}

	/*
	 * Leave an activity.
	 */
	@RequestMapping(path = "/leave", method = RequestMethod.POST)
	public ResponseEntity<?> leaveActivity(@PathVariable("activityId") String activityId) {

		String msg = null;
		try {

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			//Find if this user is already related to this activity

			ActivityUserLink link = userActivityRelationManager.checkLink(activityId, principal.getUsername());

			if(link==null){
				return HttpResponseUtil.getJsonMessage("User not a member yet", HttpStatus.BAD_REQUEST);
			}
			else if (link.getUserRelation().getStatus() == UserRelation.Status.USER_LEFT){
				return HttpResponseUtil.getJsonMessage("User " + principal.getUsername() + " "
						+ "already left activity " + activityId, HttpStatus.OK);
			}
			else {
				link.getUserRelation().setStatus(UserRelation.Status.USER_LEFT);
				userActivityRelationManager.updateRelation(link); 
			}

//			Query query = new Query(Criteria.where("id").is(activityId));
//			Update update = new Update().inc("metrics.members", -1);
//			mongoTemplate.findAndModify(query, update, Activity.class);
			this.activityManager.removeMemberFromActivity(activityId, principal.getUsername(), Member.Type.MEMBER);
			
			
			return HttpResponseUtil.getJsonMessage("User " + principal.getUsername() + 
					" successfully left activity " + activityId, HttpStatus.OK);

		}
		catch(ApiAccessException ae) {
			return ae.getHttpResponse();
		}
	}


}
