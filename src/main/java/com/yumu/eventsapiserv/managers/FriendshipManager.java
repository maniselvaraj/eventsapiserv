package com.yumu.eventsapiserv.managers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.pojos.user.Friendship;
import com.yumu.eventsapiserv.pojos.user.RegistrationInfo;
import com.yumu.eventsapiserv.pojos.user.UserWithFriendship;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.FriendshipRepository;
import com.yumu.eventsapiserv.repositories.RegistrationRepository;
import com.yumu.eventsapiserv.repositories.UserRepository;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;

@Component
public class FriendshipManager {

	@Autowired
	private FriendshipRepository friendshipRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserManager userManager;

	@Autowired
	private MongoTemplate mongoTemplate;


	@Autowired
	private RegistrationRepository regRepo;

	/*
	 * TODO: need to deal with 2 way friendship request
	 */

	public boolean areFriends(String yourId, String theirId){
		boolean found = false;
		Optional<YumuUser> yours = userRepo.findById(yourId);
		if(yours.isPresent() && yours.get().getFriends()!=null && yours.get().getFriends().contains(theirId) ){
			found = true;
		}
		if(!found){
			Optional<YumuUser> theirs = userRepo.findById(theirId);
			if(theirs.isPresent() && theirs.get().getFriends()!=null && theirs.get().getFriends().contains(yourId)){
				found = true;
			}
		}
		return found;
	}



	public Friendship checkFriendLink(String yourId, String theirId) {
		Friendship fShip = friendshipRepo.findByYourIdAndTheirId(yourId, theirId);
		return fShip;
	}

	public Friendship createPendingFriendLink(Friendship friendship) {
		friendship.setStatus(Friendship.Status.INVITE_SENT);
		friendship.setCreatedAt(new DateTime(DateTimeZone.UTC));
		Friendship savedFriendship  = friendshipRepo.save(friendship);

		/*
		 * Create the reverse link as well
		 */
		Friendship reverseLink = new Friendship();
		reverseLink.setYourId(friendship.getTheirId());
		reverseLink.setTheirId(friendship.getYourId());
		reverseLink.setCreatedAt(new DateTime(DateTimeZone.UTC));
		reverseLink.setStatus(Friendship.Status.INVITE_RECEIVED);
		friendshipRepo.save(reverseLink);
		return savedFriendship;
	}

	public void acceptFriendRequest(String yourId, String senderId) {

		Friendship link = checkFriendLink(senderId, yourId);
		if(link.getStatus()==Friendship.Status.ACTIVE){
			/*
			 * already accepted
			 */
			return;
		}
		link.setStatus(Friendship.Status.ACTIVE);
		link.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		friendshipRepo.save(link);

		/*
		 * create a reverse link as well
		 * A approves B's friend request. Now, A is a friend of B. And, B is a friend of A.
		 */
		Friendship reverseLink = checkFriendLink(yourId, senderId);
		if(reverseLink==null){
			reverseLink = new Friendship();
			reverseLink.setYourId(yourId);
			reverseLink.setTheirId(senderId);
			reverseLink.setCreatedAt(new DateTime(DateTimeZone.UTC));
		} 
		reverseLink.setStatus(Friendship.Status.ACTIVE);
		reverseLink.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		friendshipRepo.save(reverseLink);


		/*
		 * update corresponding friends list
		 *
		YumuUser yours = userRepo.findOne(yourId);
		yours.getFriends().add(senderId);
		userRepo.save(yours);
		YumuUser theirs = userRepo.findOne(senderId);
		theirs.getFriends().add(yourId);
		userRepo.save(theirs);
		 */

		/*
		 * update corresponding friends list using mongotemplate
		 */
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("id").is(yourId)), 
				new Update().push("friends", senderId), YumuUser.class);
		mongoTemplate.updateFirst(
				Query.query(Criteria.where("id").is(senderId)), 
				new Update().push("friends", yourId), YumuUser.class);
	}

	public void denyFriendRequest(String yourId, String senderId) {

		Friendship link = checkFriendLink(yourId, senderId);
		link.setStatus(Friendship.Status.DECLINED);
		link.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		friendshipRepo.save(link);

		//reverse it as well
		link = checkFriendLink(senderId, yourId);
		link.setStatus(Friendship.Status.DECLINED);
		link.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		friendshipRepo.save(link);

	}

	public void ignoreFriendRequest(String yourId, String senderId) {
		Friendship link = checkFriendLink(yourId, senderId);
		link.setStatus(Friendship.Status.IGNORED);
		link.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		friendshipRepo.save(link);

		//reverse it as well
		link = checkFriendLink(senderId, yourId);
		link.setStatus(Friendship.Status.IGNORED);
		link.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		friendshipRepo.save(link);

	}

	/*
	 * gets an users friendship info
	 */
	public List<Friendship> getFriendship(String userId) {

		return  friendshipRepo.findByYourId(userId);

	}


	/*
	 * gets an users friends
	 */
	/*
	 * Not used any where
	 */
	@Deprecated
	public List<YumuUser> getFriends(String userId) {

		List<Friendship> friends = friendshipRepo.findByYourId(userId);

		List<String> friendIds = new ArrayList<>();

		friends.forEach(link -> {
			friendIds.add(link.getTheirId());
		});

		return userRepo.findByIdIn(friendIds);
	}

	public List<RegistrationInfo> getFriendsDeviceRegId(String userId) {

		List<Friendship> friends = friendshipRepo.findByYourId(userId);
		
		List<String> friendIds = new ArrayList<>();

		friends.forEach(link -> {
			friendIds.add(link.getTheirId());
		});

		return regRepo.findByYumuUserIdInAndStatus(friendIds, RegistrationInfo.Status.LOGGED_IN.name());
	}

	/*
	 * gets an users friends
	 */
	public Page<YumuUser> getFriends(String userId, String status, Pageable page) {

		Page<Friendship> friendships = friendshipRepo.findByYourIdAndStatus(userId, status, page);

		List<String> friendIds = new ArrayList<>();

		friendships.forEach(link -> {
			friendIds.add(link.getTheirId());
		});
		//		Page<ActivityDetail> activityDetailsPage = new PageImpl<ActivityDetail>(activityDetails, 
		//pageable, allActivities.getTotalElements());

		List<YumuUser> friends = this.userManager.findByIdIn(friendIds);

		Page<YumuUser> friendsPage = new PageImpl<YumuUser>(friends, page, friendships.getTotalElements());

		//return userRepo.findByIdIn(friendIds, page);
		return friendsPage;
	}


	/*
	 * decorates user object with friendship between user and api caller
	 */
	public UserWithFriendship decorateWithFriendship(YumuUser user){

		UserWithFriendship userEx = new UserWithFriendship();
		BeanUtils.copyProperties(user, userEx);

		Authentication auth = UserAuthenticationUtils.getAuthenticatedUserNoException();
		if(auth == null || auth.getPrincipal() == null){
			return userEx;
		}
		UserDetails principal = (UserDetails) auth.getPrincipal();
		String apiActorId = principal.getUsername();

		Friendship link = this.checkFriendLink(apiActorId, user.getId());
		if(link!=null){
			userEx.setFriendship(link.getStatus());
		}
		return userEx;
	}

}

