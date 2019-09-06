package com.yumu.eventsapiserv.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.managers.FriendshipManager;
import com.yumu.eventsapiserv.managers.UserManager;
import com.yumu.eventsapiserv.managers.UserNotificationsManager;
import com.yumu.eventsapiserv.pojos.notification.UserNotification;
import com.yumu.eventsapiserv.pojos.user.Friendship;
import com.yumu.eventsapiserv.pojos.user.UserWithActivity;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.UserRepository;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;

@RestController
@RequestMapping(path = Api.BASE_VERSION + "/users")
public class UserControllerImpl {

	@Autowired 
	private UserRepository userRepo;


	@Autowired
	private FriendshipManager friendshipManager;

	@Autowired
	private UserManager userManager;


	@Autowired
	private UserNotificationsManager notificationsMgr;


	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAllUsers(Pageable page){
		Page<YumuUser> users = userRepo.findAll(page);

		List<Friendship> friends = null;
		Map<String, Friendship.Status> friendsMap = new LinkedHashMap<>();

		Authentication auth = UserAuthenticationUtils.getAuthenticatedUserNoException();
		if(auth!=null){
			UserDetails principal = (UserDetails) auth.getPrincipal();
			friends = this.friendshipManager.getFriendship(principal.getUsername());
			friends.forEach(friend -> {
				friendsMap.put(friend.getTheirId(), friend.getStatus());
			});

			List<UserWithActivity> userDetails = new ArrayList<>();

			users.forEach( _member -> {
				UserWithActivity _user = new UserWithActivity();
				BeanUtils.copyProperties(_member, _user);
				//	_member.getSocialInfo().clear();
				_user.setFriendship(friendsMap.get(_member.getId()));
				userDetails.add(_user);
			});
			Page<UserWithActivity> usersWithRelationPage = new PageImpl<UserWithActivity>(userDetails, page, users.getTotalElements());
			return new ResponseEntity<>(usersWithRelationPage, HttpStatus.OK);

		} else {

			/*
			 * dont send facebook info back
			 */
			//			users.forEach(u -> {
			//				u.getSocialInfo().clear();
			//			});
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	/*
	 * Get yumu user's profile
	 */
	//	@RequestMapping(path="/{userid}",method=RequestMethod.GET)
	//	public ResponseEntity<?> getUserById(@PathVariable("userid") String id) {
	//
	//		try {
	//			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
	//			//UserDetails principal = (UserDetails) auth.getPrincipal();
	//
	//			/*
	//			 * TODO: This is not a protected api. Anyone who knows yumu-user-id can 
	//			 * call this api and get details about about an yumu user.
	//			 */
	//			YumuUser user =  userRepo.findById(id);
	//			return new ResponseEntity<>(user, HttpStatus.OK);
	//
	//		} catch (ApiAccessException e) {
	//			return e.getHttpResponse();
	//		}
	//	}


	/*
	 * gets user details along with relationship with current actor
	 */

	@RequestMapping(path="/{userid}",method=RequestMethod.GET)
	public ResponseEntity<?> getUserById(@PathVariable("userid") String id) {

		Authentication apiActor = UserAuthenticationUtils.getAuthenticatedUserNoException();

		YumuUser user =  this.userManager.findByIdWithRelation(id, apiActor);

		//			List<ActivityCount> activityMetrics = this.userActivityRelationMgr.getUserActivityMetrics(id);
		//			if(activityMetrics!=null){
		//				if(user.getMetrics()==null){
		//					user.setMetrics(new UserMetrics());
		//				}
		//				for(ActivityCount c: activityMetrics){
		//					if(c.getId().equals("OWNER")){
		//						user.getMetrics().setActivityOwner(c.getTotal());
		//					} 
		//					else if(c.getId().equals("MEMBER")){
		//						user.getMetrics().setActivityMember(c.getTotal());
		//					}
		//				}
		//			}

		return new ResponseEntity<>(user, HttpStatus.OK);

	}



	@RequestMapping(path="/{userid}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("userid") String userId, @RequestBody YumuUser newUser ) {

		try {
			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(userId);

			YumuUser updatedUser = userManager.updateUserProfile(userId, newUser);

			return new ResponseEntity<>(updatedUser, HttpStatus.OK);

		} catch (ApiAccessException e) {
			return e.getHttpResponse();
		}
	}


	@RequestMapping(path="/{userId}/friends", method=RequestMethod.GET)
	public ResponseEntity<?> getFriends(@PathVariable("userId") String userId, 
			@RequestParam(name="status", required=false) String status,
			Pageable page){
		try {
			Authentication auth = UserAuthenticationUtils.getAuthenticatedUser();
			UserDetails principal = (UserDetails) auth.getPrincipal();

			if(StringUtils.isBlank(status)) {

				Page<YumuUser> friends = null;

				/*
				 * Not querying friendship table
				 */
				Optional<YumuUser> user = userRepo.findById(userId);
				if(user.isEmpty()){
					return HttpResponseUtil.resource_not_found(userId);
				}

				friends = userRepo.findByIdIn(user.get().getFriends(), page);

				return new ResponseEntity<>(friends, HttpStatus.OK);
			} else if(StringUtils.equalsIgnoreCase(status, "INVITE_RECEIVED") || StringUtils.equalsIgnoreCase(status, "INVITE_SENT") ){
				//return pending friends 
				Page<YumuUser> pendingFriends = this.friendshipManager.getFriends(principal.getUsername(), status, page );
				return new ResponseEntity<>(pendingFriends, HttpStatus.OK);
			} else {
				return HttpResponseUtil.getJsonMessage("Requested unknown status " + status, HttpStatus.BAD_REQUEST);
			}
		}catch (ApiAccessException e) {
			return e.getHttpResponse();
		}
		catch(Exception e){
			//TODO: log exception
			return new ResponseEntity<>(ErrorMessages.INTERNAL_ERROR_JSON.replaceFirst("ERR_MSG", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(path="/{userId}/friends", method=RequestMethod.POST)
	public ResponseEntity<?> addFriends(@PathVariable("userId") String userId, @Valid @RequestBody Friendship friendship ){
		try {
			UserAuthenticationUtils.getAuthenticatedUser();

			/*
			 * validate users
			 */
			this.userManager.checkIfExists(friendship.getYourId());
			this.userManager.checkIfExists(friendship.getTheirId());
			/*
			 * are they friends already
			 */
			if(friendshipManager.areFriends(friendship.getYourId(), friendship.getTheirId())){
				return HttpResponseUtil.getJsonMessage("Users are already active friends", HttpStatus.ALREADY_REPORTED);
			}

			/*
			 * is there a forward link: yours to theirs
			 */
			Friendship link = friendshipManager.checkFriendLink(friendship.getYourId(), friendship.getTheirId());
			if(link == null) {
				/*
				 * is there a reverse link: theirs to yours
				 */
				link = friendshipManager.checkFriendLink(friendship.getTheirId(), friendship.getYourId());
				if(link!=null){
					Map<String, Object> response = new HashMap<>();
					response.put("msg", "Reverse friend request is open from " + friendship.getTheirId() + " to you "+friendship.getYourId());
					UserNotification notification = notificationsMgr.getFriendRequestNotification(friendship.getTheirId(), friendship.getYourId());
					if(notification!=null){
						response.put("notification", notification);
					}
					return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
				}
			}

			if(link != null){
				//You have already sent a friend request
				Map<String, Object> response = new HashMap<>();
				response.put("msg", "Friend request is open from you " + friendship.getTheirId() + " to "+friendship.getYourId());
				UserNotification notification = notificationsMgr.getFriendRequestNotification(friendship.getYourId(), friendship.getTheirId());
				if(notification!=null){
					response.put("notification", notification);
				}
				return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
			} else {
				Friendship savedFriendship  = friendshipManager.createPendingFriendLink(friendship);
				notificationsMgr.sendFriendRequestNotification(savedFriendship.getYourId(), savedFriendship.getTheirId());
			}

			return HttpResponseUtil.getJsonMessage("Successfully sent friendship invite to " + friendship.getTheirId(), HttpStatus.OK);
			//return new ResponseEntity<>(null, HttpStatus.OK);
		}
		catch (ApiAccessException e) {
			return e.getHttpResponse();
		}
	}


	/*
	 * Note: why am I approving friendship through notification manager.
	 * Answer: Notification based friendship was implemented first. Due to lack of time
	 * direct friendship action is piped through notification manager instead of the other way.
	 */
	@RequestMapping(path="{yourId}/friends/{theirId}/{action}", method=RequestMethod.POST)
	public ResponseEntity<?> doFriendshipAction(@PathVariable("yourId") String yourId,
			@PathVariable("theirId") String theirId,
			@PathVariable("action") String action){

		try {

			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(yourId);
			/*
			 * get the pending notification and act on it.
			 */
			UserNotification notification = this.notificationsMgr.getFriendRequestNotification(theirId, yourId);

			if(notification!=null){

				if(StringUtils.equalsIgnoreCase(action, "approve")) {
					this.notificationsMgr.approve(yourId, notification);
					return HttpResponseUtil.getJsonMessage("User " + yourId + " became friends with " + theirId, HttpStatus.OK);
				} 
				else if(StringUtils.equalsIgnoreCase(action, "deny")) {
					this.notificationsMgr.deny(yourId, notification);
					return HttpResponseUtil.getJsonMessage("User " + yourId + " denied friend request from " + theirId, HttpStatus.OK);
				}
				else if(StringUtils.equalsIgnoreCase(action, "ignore")) {
					this.notificationsMgr.ignore(yourId, notification);
					return HttpResponseUtil.getJsonMessage("User " + yourId + " ignored friend request from " + theirId, HttpStatus.OK);
				} else {
					return HttpResponseUtil.getJsonMessage("Unknown action " + action , HttpStatus.BAD_REQUEST);
				}
			}
			else {
				return HttpResponseUtil.getJsonMessage("Unable to find pending friend request for " + yourId + " from " + theirId, 
						HttpStatus.NOT_FOUND);
			}
		}
		catch(ApiAccessException e) {
			return e.getHttpResponse();
		}
	}

}
