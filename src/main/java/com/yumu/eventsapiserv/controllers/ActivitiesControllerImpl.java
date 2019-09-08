package com.yumu.eventsapiserv.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.managers.ActivityManager;
import com.yumu.eventsapiserv.managers.AppLogManager;
import com.yumu.eventsapiserv.managers.FriendshipManager;
import com.yumu.eventsapiserv.managers.PostsManager;
import com.yumu.eventsapiserv.managers.UserActivitiesRelationshipManager;
import com.yumu.eventsapiserv.managers.UserManager;
import com.yumu.eventsapiserv.managers.UserNotificationsManager;
import com.yumu.eventsapiserv.pojos.activities.Activity;
import com.yumu.eventsapiserv.pojos.activities.UserRelation;
import com.yumu.eventsapiserv.pojos.activities.UserRelation.UserType;
import com.yumu.eventsapiserv.pojos.activities.Activity.Status;
import com.yumu.eventsapiserv.pojos.activities.ActivityDetail;
import com.yumu.eventsapiserv.pojos.activities.ActivityUserLink;
import com.yumu.eventsapiserv.pojos.activities.Member;
import com.yumu.eventsapiserv.pojos.activities.Post;
import com.yumu.eventsapiserv.pojos.activities.PostDetail;
import com.yumu.eventsapiserv.pojos.common.Location;
import com.yumu.eventsapiserv.pojos.common.Metrics;
import com.yumu.eventsapiserv.pojos.common.TimeInfo;
import com.yumu.eventsapiserv.pojos.user.Friendship;
import com.yumu.eventsapiserv.pojos.user.UserWithActivity;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.pojos.notification.*;
import com.yumu.eventsapiserv.repositories.ActivityRepository;
import com.yumu.eventsapiserv.repositories.PostRepository;
import com.yumu.eventsapiserv.tasks.TaskGenerator;
import com.yumu.eventsapiserv.utils.ActivityUtil;
import com.yumu.eventsapiserv.utils.ActivityValidator;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;
import com.yumu.eventsapiserv.utils.LogUtil;
import com.yumu.eventsapiserv.utils.TimeInfoUtil;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;



/**
 * Main controller for activity aka this is where it all started!!!
 * @author mani
 *
 */

@RestController
@RequestMapping(path = Api.BASE_VERSION + "/core/activities")
public class ActivitiesControllerImpl {

	private final static Logger logger = LogManager.getLogger(ActivitiesControllerImpl.class);

	@Autowired
	private AppLogManager dblogger;
	
	/*
	 * Stores the core activity data
	 */
	@Autowired
	private ActivityRepository activitiesRepo;

	@Autowired
	private ActivityManager activityManager;

	@Autowired
	private FriendshipManager friendsManager;

	@Autowired
	private PostsManager postsManager;

	@Autowired
	private MongoTemplate mongoTemplate;

	/*
	 * stores the relation between activity and user. Kind of SQL mapping table.
	 */
	@Autowired
	private UserActivitiesRelationshipManager userActivityRelationManager;

	@Autowired
	private PostRepository postsRepo;

	@Autowired
	private UserNotificationsManager notificationsManager;

	@Autowired
	private TaskGenerator taskQ;

	@Autowired
	private UserManager userMgr;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exceptionHandler(Exception e) {
		// do something. e.g. customize error response
		e.printStackTrace();
		Map<String, String> msg = new HashMap<>();
		msg.put("msg", e.getMessage());
		return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	/*
	 * Main api to create one activity
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody Activity activity, BindingResult result) {


		Activity savedActivity = null;
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			/*
			 * only logged in users can create
			 */
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();

			//TODO: should move this validation error check to upper level
			if (result.hasErrors()) {
				List<FieldError> errors = result.getFieldErrors();
				return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
			}

			this.logger.debug("ACTIVITY create called for: " + activity.getName());

			/*
			 * Do basic validation
			 */
			
			ActivityValidator.basicValidation(activity);
			
			/*
			 * get the user who is creating this
			 */
			//YumuUser owner = new YumuUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();
			//owner.setId(principal.getUsername());
			List<String> owners = new ArrayList<>();
			owners.add(principal.getUsername());
			activity.setOwners(owners);

			/*
			 * Also add to member list
			 */
			List<Member> members = new ArrayList<>();
			Member owner = new Member();
			owner.setYumuUserId(principal.getUsername());
			owner.setType(Member.Type.OWNER);
			members.add(owner);
			activity.setMembers(members);

			/*
			 * init metrics
			 */
			if(activity.getMetrics()==null){
				Metrics metrics = new Metrics();
				metrics.setVisits(1);
				metrics.setMembers(1);
				//TODO star rating
				Double stars = ThreadLocalRandom.current().nextDouble(5.0);
				metrics.setStars(Math.round(stars*10.0)/10.0);
				activity.setMetrics(metrics);
			}

			/*
			 * init status
			 */
			activity.setStatus(Status.ACTIVE);


			/*
			 * init time info
			 */
			TimeInfo timeInfo = activity.getTimeInfo();
			if(timeInfo == null){
				timeInfo = new TimeInfo();
				activity.setTimeInfo(timeInfo);
			} 
			timeInfo.setCreatedAt(new DateTime(DateTimeZone.UTC));
			timeInfo.setUpdatedAt(new DateTime(DateTimeZone.UTC));

			/*
			 * Extract tags
			 */
			activity.getHashtags().addAll(ActivityUtil.extractHashTags(activity.getDescription()));
			activity.getHashtags().addAll(ActivityUtil.extractHashTags(activity.getName()));

			try {
				savedActivity = activitiesRepo.save(activity);
			}
			catch(javax.validation.ConstraintViolationException e){

				e.printStackTrace();

				for(ConstraintViolation<?> v: e.getConstraintViolations()){
					System.out.println("MDEBUG " + v.getMessage() + " : " + v.getPropertyPath());
				}
				return null;
			}
			/*
			 * now store the relationship between activity and user
			 */
			userActivityRelationManager.createLink(savedActivity, 
					principal.getUsername(), 
					UserRelation.UserType.OWNER,
					UserRelation.Status.ACTIVE);


			YumuUser ownerAcct = this.userMgr.findById(principal.getUsername());
			String body = ownerAcct.getNameInfo().getScreenName() + " created activity \"" + savedActivity.getName() + "\"";

			this.taskQ.generateActivityCreationNotification(savedActivity.getId(), 
					principal.getUsername(), body);
			
			this.dblogger.info("ACTIVITY CREATE: " + body);
			this.logger.debug("MDEBUG " + body);

		}
		catch(ApiAccessException ae){
			return ae.getHttpResponse();
		}
		catch(Exception e){
			/*
			 * TODO: Use mongodb collection which expires by size, time, or count for logging
			 */
			String msg = LogUtil.stackToString(e);
			System.out.println("MDEBUG Exception while saving relation: " + msg);
			logger.error("Exception while saving activity-relation: " + msg);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(savedActivity, httpStatus);
	}



	/*
	 * api to update one activity
	 */
	@RequestMapping(method = RequestMethod.PUT, path="/{activityId}")
	public ResponseEntity<?> update(@RequestBody Activity activity, @PathVariable("activityId") String activityId) {

		HttpStatus httpStatus = HttpStatus.OK;
		Activity storedActivity = null;
		try {
			/*
			 * only logged in users can create
			 */
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails user = (UserDetails) auth.getPrincipal();
			String userId = user.getUsername();

			/*
			 * make sure that user is one of the owners
			 */
			storedActivity = activityManager.loadById(activityId);

			activityManager.checkIfOwner(userId, storedActivity);

			Location newLocation = activity.getLocation();
			if(newLocation!=null){
				if(!storedActivity.getLocation().equals(newLocation)) {
					throw new ApiAccessException(ErrorMessages.CANNOT_UPDATE_LOCATION, HttpStatus.BAD_REQUEST);
				}
			}



			/*
			 * copy the incoming activity into 
			 */
			if(activity.getAccessType()!=null){
				storedActivity.setAccessType(activity.getAccessType());
			}
			if(StringUtils.isNotBlank(activity.getName())){
				storedActivity.setName(activity.getName());
			}
			if(StringUtils.isNotBlank(activity.getDescription())){
				storedActivity.setDescription(activity.getDescription());
			}
			if(activity.getStatus()!=null){
				storedActivity.setStatus(activity.getStatus());
			}


			TimeInfo oldTime = storedActivity.getTimeInfo();
			if(oldTime == null){
				oldTime = new TimeInfo();
				//oldTime.setCreatedAt(new Date());
				storedActivity.setTimeInfo(oldTime);
			}
			oldTime.setUpdatedAt(new DateTime());
			TimeInfo newTime = activity.getTimeInfo();
			TimeInfoUtil.compareAndCopy(newTime, oldTime);

			/*
			 * update the hashtags
			 */
			/*
			 * Extract tags
			 */
			storedActivity.getHashtags().clear();
			storedActivity.getHashtags().addAll(ActivityUtil.extractHashTags(activity.getDescription()));
			storedActivity.getHashtags().addAll(ActivityUtil.extractHashTags(activity.getName()));

			activitiesRepo.save(storedActivity);

		}
		catch(ApiAccessException ae){
			return ae.getHttpResponse();
		}
		catch(Exception e){
			e.printStackTrace();
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(storedActivity, httpStatus);
	}

	@RequestMapping(method=RequestMethod.GET, path="/meta")
	public ResponseEntity<?> getActivityMetaData(){

		Map<String, Object> response = new HashMap<>();

		response.put("access_type", Activity.AccessType.values());
		response.put("status", Activity.Status.values());
		response.put("type", Activity.Type.values());
		response.put("repeats_every", TimeInfo.Repeats.values());
		response.put("user_relation.user_type", UserRelation.UserType.values());
		response.put("user_relation.status", UserRelation.Status.values());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/*
	 * get all activities. Pretty much everything. Does not check for logged in
	 * user.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getGroupActivities(@RequestHeader(value="X-YUMU-LOCATION", required=false) String location,
			Pageable pageable) {

		String msg = "";
		Page<Activity> allActivities = null;

		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			/*
			 * !!!Tricky
			 * get activities for logged in user. 
			 * Dont show his own activities again
			 */

			UserDetails user = (UserDetails) auth.getPrincipal();
			
			this.logger.debug("GET GROUP ACTIVITIES for user " + user.getUsername() + "  location: " + location);
			
			allActivities = this.activityManager.findGroupActivitiesForUser(
														Activity.Status.ACTIVE, 
														user.getUsername(), 
														location, 
														pageable);
			msg = "User: " + user.getUsername();
		} else {
			
			this.logger.debug("GET GROUP ACTIVITIES for guest  location: " + location);
			
			allActivities = this.activityManager.findGroupActivitiesForGuest(
													Activity.Status.ACTIVE, 
													location, 
													pageable);
		}


		if(allActivities==null || allActivities.getSize()==0){
			return HttpResponseUtil.getJsonMessage("No group activities found", HttpStatus.NOT_FOUND);
		}


		List<ActivityDetail> activityDetails = new ArrayList<>();
		/*
		 * Populate the relation between the user and activity
		 */
		allActivities.forEach(act -> {
			ActivityDetail detail = this.activityManager.decorateActivityWithDetail(act);
			activityDetails.add(detail);
		});

		Page<ActivityDetail> activityDetailsPage = new PageImpl<ActivityDetail>(activityDetails, 
				pageable, allActivities.getTotalElements());


		msg = msg + ": GET GRP ACT name: " + allActivities.getContent().get(0).getName();
		this.dblogger.info(msg);
		this.logger.debug(msg);
		MultiValueMap<String, String> headers = HttpResponseUtil.getNoCacheHeader();
		return new ResponseEntity<>(activityDetailsPage, headers, HttpStatus.OK);
	}

	/*
	 * Get one activity based on its id
	 */
	@RequestMapping(path = "/{activityId}", method = RequestMethod.GET)
	public ResponseEntity<?> getActivityById(@PathVariable("activityId") String id) {
		
		this.logger.debug("ACTIVITY GET by id " + id);
		//Activity activity = activitiesRepo.findOne(id);
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update().inc("metrics.visits", 1);

		Activity activity = mongoTemplate.findAndModify(query, update, Activity.class);

		if(activity==null){
			return HttpResponseUtil.resource_not_found(id);
		}

		ActivityDetail detail = this.activityManager.decorateActivityWithDetail(activity);
		
		String msg = "GET ACT name: " + activity.getName() + " postcount: " + activity.getMetrics().getPosts();
		this.dblogger.info(msg);
		this.logger.debug(msg);

		return new ResponseEntity<>(detail, HttpStatus.OK);
	}

	/*
	 * create one post under an activity.
	 */
	@RequestMapping(path = "/{activityId}/posts", method = RequestMethod.POST)
	public ResponseEntity<?> createPostInThisActivity(@PathVariable("activityId") String activityId,
			@Valid @RequestBody Post post, BindingResult result) {

		try {
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			/*
			 * TODO: check if user is allowed to post in this activity. Need to see if the link is active and
			 * user is not blocked
			 */
			ActivityUserLink link = this.userActivityRelationManager.checkLink(activityId, principal.getUsername());
			if(link==null || link.getUserRelation().getStatus()!= UserRelation.Status.ACTIVE){
				return HttpResponseUtil.getJsonMessage("User not allowed: " + principal.getUsername(), HttpStatus.UNAUTHORIZED);
			}

			/*
			 * validate the activity and update it
			 */
			activityManager.updatePostCount(activityId, 1);


			post.setOwner(principal.getUsername());
			post.setCreatedAt(new DateTime(DateTimeZone.UTC));
			post.setUpdatedAt(new DateTime(DateTimeZone.UTC));
			post.setStatus(Post.Status.ACTIVE);

			if (result.hasErrors()) {
				List<FieldError> errors = result.getFieldErrors();
				return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
			}

			post.setActivityId(activityId);

			post.getHashtags().addAll(ActivityUtil.extractHashTags(post.getContent()));

			Post savedPost = postsRepo.save(post);
			PostDetail detail = postsManager.decoratePostWithDetail(savedPost);
			
			this.dblogger.info("POST CREATE: " + post.getContent());
			
			this.logger.debug("MDEBUG POST CREATE: " + post.getContent());
			
			return new ResponseEntity<>(detail, HttpStatus.OK);

		} catch (ApiAccessException e){
			return e.getHttpResponse();
		}

	}

	/*
	 * update one post under an activity. 
	 */
	@RequestMapping(path = "/{activityId}/posts/{postId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePostInThisActivity(@PathVariable("activityId") String activityId,
			@PathVariable("postId") String postId,
			@Valid @RequestBody Post post, BindingResult result) {

		try {
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();

			UserDetails principal = (UserDetails) auth.getPrincipal();

			/*
			 * validate the activity and update it
			 */
			Activity activity = activityManager.loadById(activityId);

			/*
			 * TODO: check if user is allowed to post in this activity. Need to see if the link is active and
			 * user is not blocked
			 */
			ActivityUserLink link = this.userActivityRelationManager.getActivityUserLink(activity.getId(), 
					principal.getUsername());
			if(link==null){
				return HttpResponseUtil.getJsonMessage("Must be a member to add a post", 
						HttpStatus.UNAUTHORIZED);
			}

			/*
			 * check if user is the owner of the post
			 */
			Post savedPost = this.postsManager.getPostById(postId);
			if(!savedPost.getOwner().equals(principal.getUsername())) {
				return HttpResponseUtil.getJsonMessage("User is not the owner of this post", 
						HttpStatus.UNAUTHORIZED);
			}

			if (result.hasErrors()) {
				List<FieldError> errors = result.getFieldErrors();
				return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
			}

			savedPost.setContent(post.getContent());
			savedPost.getHashtags().clear();
			savedPost.getHashtags().addAll(ActivityUtil.extractHashTags(post.getContent()));

			savedPost.setUpdatedAt(new DateTime(DateTimeZone.UTC));
			postsRepo.save(savedPost);
			PostDetail detail = postsManager.decoratePostWithDetail(savedPost);
			return new ResponseEntity<>(detail, HttpStatus.OK);

		} catch (ApiAccessException e){
			return e.getHttpResponse();
		}

	}


	/*
	 * update one post under an activity. 
	 */
	@RequestMapping(path = "/{activityId}/posts/{postId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePostInThisActivity(@PathVariable("activityId") String activityId,
			@PathVariable("postId") String postId) {

		try {
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			/*
			 * validate the activity and update it
			 */
			Activity activity = activityManager.loadById(activityId);

			/*
			 * TODO: check if user is allowed to post in this activity. Need to see if the link is active and
			 * user is not blocked
			 */
			ActivityUserLink link = this.userActivityRelationManager.getActivityUserLink(activity.getId(), 
					principal.getUsername());
			if(link==null){
				return HttpResponseUtil.getJsonMessage("Not authorized to delete this post", 
						HttpStatus.UNAUTHORIZED);
			}

			/*
			 * check if user is the owner of the post or owner of this activity
			 */
			Post savedPost = this.postsManager.getPostById(postId);
			//			if(!savedPost.getOwner().equals(principal.getUsername()) && 
			//					!activity.getOwners().contains(principal.getUsername())) {
			//				return HttpResponseUtil.getJsonMessage("User is not the owner of this post or activity", 
			//						HttpStatus.UNAUTHORIZED);
			//			}

			if(savedPost.getOwner().equals(principal.getUsername()) || 
					activity.getOwners().contains(principal.getUsername())) {
				savedPost.setUpdatedAt(new DateTime(DateTimeZone.UTC));
				savedPost.setStatus(Post.Status.DELETED);
				postsRepo.save(savedPost);

				this.activityManager.updatePostCount(activityId, -1);

				return HttpResponseUtil.getJsonMessage("User "+ principal.getUsername() 
				+ " deleted post " + savedPost.getId(),	HttpStatus.OK);
			} else {
				return HttpResponseUtil.getJsonMessage("User is not the owner of this post or activity", 
						HttpStatus.UNAUTHORIZED);
			}

		} catch (ApiAccessException e){
			return e.getHttpResponse();
		}

	}



	/*
	 * Get all posts under one activity
	 */
	@RequestMapping(path = "/{activityId}/posts", method = RequestMethod.GET)
	public ResponseEntity<?> getPostsByActivityId(@PathVariable("activityId") String activityId, 
			@RequestParam(name="status", required=false) String status,
			Pageable pageable) {
		
		this.logger.debug("GET ACTIVITY POSTS for act: " + activityId);

		if(StringUtils.isBlank(status) || StringUtils.equalsIgnoreCase(status, "active")){
			status=Post.Status.ACTIVE.name();;
		} else if(StringUtils.equalsIgnoreCase(status, "deleted")){
			status = Post.Status.DELETED.name();
		}else if(StringUtils.equalsIgnoreCase(status, "disabled")){
			status = Post.Status.DISABLED.name();
		}
		return postsManager.getPostsInActivity(activityId, status, pageable);
	}

	/*
	 * Get all posts under one activity
	 */
	@RequestMapping(path = "/{activityId}/posts/{postId}", method = RequestMethod.GET)
	public ResponseEntity<?> getPostsById(@PathVariable("activityId") String activityId, 
			@PathVariable("postId") String postId) {

		Post post = this.postsManager.getPostById(postId);

		if(!post.getActivityId().equals(activityId)){
			return HttpResponseUtil.getJsonMessage("Post " + postId + 
					" does not belong to activity: "+activityId, HttpStatus.BAD_REQUEST);
		}

		PostDetail pd = this.postsManager.decoratePostWithDetail(post);

		return new ResponseEntity<>(pd, HttpStatus.OK);

	}

	/*
	 * Get all members in one activity.
	 * Use status to filter the members: PENDING, INVITATION_SENT etc
	 */
	@RequestMapping(path = "/{activityId}/members", method = RequestMethod.GET)
	public ResponseEntity<?> getMembersByActivityId(@PathVariable("activityId") String activityId, 
			@RequestParam(name="status", required=false) String status,
			Pageable page) {

		try {
			Page<YumuUser> members = null;

			if(StringUtils.equalsIgnoreCase(status, "PENDING")) {
				members = userActivityRelationManager.getPendingMembers(activityId, page);
			}
			else 
			if(StringUtils.equalsIgnoreCase(status, "POTENTIAL")) {
				/*
				 * potential members are pretty much all yumu users who are not a member yet
				 */
				members = this.activityManager.getPotentialMembers(activityId, page);
			}
			else {
				members = userActivityRelationManager.getActiveMembers(activityId, page);
			}

			if(members==null || members.getSize()==0){
				new ResponseEntity<>(members, HttpStatus.OK);
			}

			List<UserWithActivity> users = new ArrayList<>();

			//get all link for this activity
			Map<String, ActivityUserLink> links = userActivityRelationManager.getActivityUserLinksMap(activityId, true);

			List<Friendship> friends = null;
			Map<String, Friendship.Status> friendsMap = new LinkedHashMap<>();

			Authentication auth = UserAuthenticationUtils.getAuthenticatedUserNoException();
			if(auth!=null){
				UserDetails principal = (UserDetails) auth.getPrincipal();
				friends = this.friendsManager.getFriendship(principal.getUsername());
				friends.forEach(friend -> {
					friendsMap.put(friend.getTheirId(), friend.getStatus());
				});
			}

			members.forEach( _member -> {
				UserWithActivity _user = new UserWithActivity();
				BeanUtils.copyProperties(_member, _user);
				_user.setActivityRelation(links.get(_member.getId()));
				_user.setFriendship(friendsMap.get(_member.getId()));
				users.add(_user);
			});

			Page<UserWithActivity> usersWithRelationPage = new PageImpl<UserWithActivity>(users, page, members.getTotalElements());

			return new ResponseEntity<>(usersWithRelationPage, HttpStatus.OK);
		}
		catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}



	/*
	 * api to update one activity
	 */
	@RequestMapping(method = RequestMethod.DELETE, path="/{activityId}")
	public ResponseEntity<?> delete(@PathVariable("activityId") String activityId) {

		Activity storedActivity = null;
		try {
			/*
			 * only logged in users can do this
			 */
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails user = (UserDetails) auth.getPrincipal();
			String userId = user.getUsername();

			/*
			 * make sure that user is one of the owners
			 */
			storedActivity = activityManager.loadById(activityId);

			activityManager.checkIfOwner(userId, storedActivity);

			if(storedActivity.getStatus()==Status.DELETED){
				return HttpResponseUtil.getJsonMessage("Activity already deleted", HttpStatus.ALREADY_REPORTED);
			}

			storedActivity.getTimeInfo().setUpdatedAt(new DateTime(DateTimeZone.UTC));
			storedActivity.setStatus(Status.DELETED);

			activitiesRepo.save(storedActivity);

			this.userActivityRelationManager.setActivityLinkStatus(activityId, UserRelation.Status.ACTIVITY_DELETED);

			return HttpResponseUtil.getJsonMessage("Activity successfully deleted", HttpStatus.ALREADY_REPORTED);

		}
		catch(ApiAccessException ae){
			return ae.getHttpResponse();
		}
		catch(Exception e){
			return HttpResponseUtil.getJsonMessage("Activity deletion failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(method=RequestMethod.PUT, path="/{activityId}/members/{pendingMemberId}/{action}")
	public ResponseEntity<?> updateMemberStatus(@PathVariable("activityId") String activityId,
			@PathVariable("pendingMemberId") String memberId,
			@PathVariable("action") String action){

		Map<String, String> msgs = new HashMap<>();

		try {
			/*
			 * only logged in users can create
			 */
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails user = (UserDetails) auth.getPrincipal();


			/*
			 * check if memberId is related to the activity 
			 */
			ActivityUserLink link = this.userActivityRelationManager.checkLink(activityId, memberId);
			if(link==null){
				return HttpResponseUtil.getJsonMessage("Not a member or pending member: "+memberId, HttpStatus.BAD_REQUEST);
			}

			/*
			 * get the activity
			 */
			Activity activity = this.activityManager.loadById(activityId);

			this.activityManager.checkIfOwner(user.getUsername(), activity);


			msgs.put("activity_id", activityId);
			msgs.put("yumu_user_id", memberId);
			msgs.put("action", action);

			if(StringUtils.equalsIgnoreCase(action, "APPROVE")) {
				this.userActivityRelationManager
				.approveMembership(activity, memberId, UserType.MEMBER);
			} 
			else if(StringUtils.equalsIgnoreCase(action, "DECLINE")) {
				this.userActivityRelationManager
				.denyMembership(activity, memberId, UserType.MEMBER);
			}
			else if(StringUtils.equalsIgnoreCase(action, "BLOCK")){
				this.userActivityRelationManager
				.blockMembership(activity, memberId, UserType.MEMBER);
			}
			else if(StringUtils.equalsIgnoreCase(action, "UNBLOCK")){
				this.userActivityRelationManager
				.approveMembership(activity, memberId, UserType.MEMBER);
			} else {
				return HttpResponseUtil.getJsonMessage("Unsupported operation " 
						+ action + " invoked.", HttpStatus.BAD_REQUEST);
			}

			/*
			 * need to take care of the pending notification to the owner
			 */
			this.notificationsManager.updateNotification(  memberId, 
					user.getUsername(), 
					UserNotification.Type.MEMBER_REQUEST, 
					Context.Type.ACTIVITY, 
					activityId,
					UserNotification.Status.ACCEPT);

			return HttpResponseUtil.getJsonMessage("Action " + action  + " performed on member " 
					+ memberId, HttpStatus.OK);

		}
		catch(ApiAccessException ae){
			return ae.getHttpResponse();
		}
	}

	@RequestMapping(method=RequestMethod.POST, path="/{activityId}/members/{action}")
	public ResponseEntity<?> updateAllMemberStatus(@PathVariable("activityId") String activityId,
			@PathVariable("action") String action){

		try {
			/*
			 * only logged in users can create
			 */
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails user = (UserDetails) auth.getPrincipal();

			Activity activity = this.activityManager.loadById(activityId);
			this.activityManager.checkIfOwner(user.getUsername(), activity);

			if(StringUtils.equalsIgnoreCase(action, "APPROVEALL")) {
				/*
				 * get all pending members:
				 */
				List<YumuUser> members = userActivityRelationManager.getAllPendingMembers(activityId);

				members.forEach(member -> {
					try {
						this.userActivityRelationManager.approveMembership(activity, member.getId(), UserType.MEMBER);
						this.notificationsManager.updateNotification( member.getId(), 
								user.getUsername(), 
								UserNotification.Type.MEMBER_REQUEST, 
								Context.Type.ACTIVITY, 
								activityId,
								UserNotification.Status.ACCEPT);

					} catch (Exception e) {
						//TODO: silently ignore and proceed to next
					}
				});

				return HttpResponseUtil.getJsonMessage("All pending members approved", HttpStatus.OK);
			}


			return HttpResponseUtil.getJsonMessage("Unsupported action: " + action, HttpStatus.BAD_REQUEST);
		}
		catch(ApiAccessException e){
			return e.getHttpResponse();
		}

	}

}
