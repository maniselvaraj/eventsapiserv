package com.yumu.eventsapiserv.managers;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.pojos.activities.Activity;
import com.yumu.eventsapiserv.pojos.activities.ActivityDetail;
import com.yumu.eventsapiserv.pojos.activities.ActivityUserLink;
import com.yumu.eventsapiserv.pojos.activities.Member;
import com.yumu.eventsapiserv.pojos.common.Metrics;
import com.yumu.eventsapiserv.pojos.common.TimeInfo;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.ActivityRepository;
import com.yumu.eventsapiserv.repositories.UserRepository;
import com.yumu.eventsapiserv.utils.ActivityUtil;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;



@Component
public class ActivityManager {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ActivityRepository activityRepo;
	
	@Autowired
	private UserActivitiesRelationshipManager userActivityRelationManager;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final static Logger logger = LogManager.getLogger(ActivityManager.class);
	
	public Activity loadById(String id) throws ApiAccessException{
		Optional<Activity> activity = activityRepo.findById(id);
		if(activity.isEmpty()){
			throw new ApiAccessException(ErrorMessages.RESOURCE_NOT_FOUND + "activity_id:"+id, HttpStatus.NOT_FOUND);
		}
		return activity.get();
	}
	
	public Activity checkIfActive(String id) throws ApiAccessException{
		Optional<Activity> activity = activityRepo.findById(id);
		if(activity.isEmpty()){
			throw new ApiAccessException(ErrorMessages.RESOURCE_NOT_FOUND + "activity_id:"+id, HttpStatus.NOT_FOUND);
		}
		else if(activity.get().getStatus()!= Activity.Status.ACTIVE){
			throw new ApiAccessException(ErrorMessages.RESOURCE_NOT_ACTIVE + "activity_id:"+id, HttpStatus.BAD_REQUEST);
		}
		return activity.get();
	}
	
	public boolean checkIfOwner(String userId, Activity activity) throws ApiAccessException {
//		List<YumuUser> owners = activity.getOwners();
//		boolean bOwner = false;
//		for(YumuUser u: owners){
//			if(u.getId().equals(userId)){
//				bOwner = true;
//				break;
//			}
//		}
		if(!activity.getOwners().contains(userId)){
			throw new ApiAccessException(ErrorMessages.USER_NOT_A_OWNER, HttpStatus.FORBIDDEN);
		}
		return true;
	}
	

	public Activity updatePostCount(String activityId, int i) throws ApiAccessException {
		Optional<Activity> oActivity = activityRepo.findById(activityId);
		if(oActivity.isEmpty()){
			throw new ApiAccessException(ErrorMessages.RESOURCE_NOT_FOUND + "activity_id:"+activityId, HttpStatus.NOT_FOUND);
		}
		
		Activity activity  = oActivity.get();
		Metrics metrics = activity.getMetrics();
		if(metrics==null){
			metrics = new Metrics();
			activity.setMetrics(metrics);
		}
		Integer numPosts = metrics.getPosts();
		if(numPosts==null){
			numPosts = new Integer("0");
		}
		numPosts+=i;
		metrics.setPosts(numPosts);
		
		TimeInfo time = activity.getTimeInfo();
		if(time==null){
			//Just in case
			time = new TimeInfo();
		}
		time.setUpdatedAt(new DateTime());
		
		return activityRepo.save(activity);
	}
	
	
	public void updateMemberCount(String activityId, int count){
		Query query = new Query(Criteria.where("id").is(activityId));
		Update update = new Update().inc("metrics.members", count);
		mongoTemplate.findAndModify(query, update, Activity.class);
	}
	
	/*
	 * This adder adds a member to activity document. Needed this for querying activities 
	 * to which an user is not connected
	 */
	public void addMemberToActivity(String activityId, String userId, Member.Type type){
		Query query = new Query(Criteria.where("id").is(activityId));
		Member member = new Member();
		member.setYumuUserId(userId);
		member.setType(type);
		Update update = new Update().addToSet("members", member).inc("metrics.members", 1);
		mongoTemplate.findAndModify(query, update, Activity.class);
	}
	
	public void removeMemberFromActivity(String activityId, String userId, Member.Type type){
		Query query = new Query(Criteria.where("id").is(activityId));
		Member member = new Member();
		member.setYumuUserId(userId);
		member.setType(type);
		Update update = new Update().pull("members", member).inc("metrics.members", -1);
		mongoTemplate.findAndModify(query, update, Activity.class);
	}
	
	public ActivityDetail decorateActivityWithDetail(Activity activity) {
		
		
		ActivityDetail detail = new ActivityDetail();
		BeanUtils.copyProperties(activity, detail);
		
		List<String> owners = activity.getOwners();
		List<YumuUser> users = this.userRepo.findByIdIn(owners);
		/*
		 * TODO: dont send facebook details back to user
		 */
		//users.forEach(usr -> {
		//	usr.getSocialInfo().clear();
		//});
		detail.getOwners().clear();
		detail.setOwners(null);
		detail.setOwnersInfo(users);
		
		/*
		 * if caller is a logged in user, then send the relation between this activity and user also
		 */
		Authentication auth = UserAuthenticationUtils.getAuthenticatedUserNoException();
		if(auth!=null){
			UserDetails principal = (UserDetails) auth.getPrincipal();
			ActivityUserLink link = userActivityRelationManager.getActivityUserLink(activity.getId(), 
					principal.getUsername());
			if(link!=null){
				link.setLocation(null);
				detail.setActivityRelation(link);
			} 
//			else {
//				System.out.println("MDEBUG no link between activity: "+ activity.getId() + " and user:"+principal.getUsername());;
//			}
		}
		
		return detail;
		
	}
	
	public Page<Activity> findGroupActivitiesForGuest(Activity.Status status, String location, Pageable page){
		
		Circle circle = null;
		if(StringUtils.isNotBlank(location)){
			String[] elements = location.split(",");
			if(elements.length==3 
					&& StringUtils.isNotBlank(elements[0])
					&& StringUtils.isNotBlank(elements[1])
					&& StringUtils.isNotBlank(elements[2])) {
				double x = Double.valueOf(elements[0]);
				double y = Double.valueOf(elements[1]);
				
				double radius = ActivityUtil.getMinimumRadius(elements[2]);
				
				circle = new Circle( x, y, radius);
			}
		} 

		if(circle!=null) {
			return this.activityRepo.findByStatusAndLocationPointWithin(status, circle, page);
		} else {
			return this.activityRepo.findByStatus(status, page);
		}
		
	}
	
	public Page<Activity> findGroupActivitiesForUser(Activity.Status status, String userId, String location, Pageable page){
		
		Circle circle = null;
		if(StringUtils.isNotBlank(location)){
			String[] elements = location.split(",");
			if(elements.length==3 
					&& StringUtils.isNotBlank(elements[0])
					&& StringUtils.isNotBlank(elements[1])
					&& StringUtils.isNotBlank(elements[2])) {
				double x = Double.valueOf(elements[0]);
				double y = Double.valueOf(elements[1]);
				double radius = ActivityUtil.getMinimumRadius(elements[2]);
				circle = new Circle( x, y, radius);
			}
		} 

		if(circle!=null) {
			return this.activityRepo.findByStatusAndLocationPointWithinAndMembersYumuUserIdNot(status, circle, userId, page);
		} else {
			return this.activityRepo.findByStatusAndMembersYumuUserIdNot(status, userId, page);
		}
		
	}

	public Page<YumuUser> getPotentialMembers(String activityId, Pageable page) throws ApiAccessException {
		
		Activity activity = this.loadById(activityId);
		if(activity==null){
			return null;
		}
		
//		List<String> existingMembers = new ArrayList<>();
//		activity.getMembers().forEach(m -> {
//			existingMembers.add(m.getYumuUserId());
//		});
		
		//Alternate using streams
		List<String> existingMembers = activity.getMembers()
				.stream()
				.map( m -> m.getYumuUserId())
				.collect(Collectors.toList());
		
		return this.userRepo.findByIdNotIn(existingMembers, page);
	}
	
}


