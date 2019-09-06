package com.yumu.eventsapiserv.managers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.pojos.activities.Activity;
import com.yumu.eventsapiserv.pojos.activities.UserRelation.UserType;
import com.yumu.eventsapiserv.pojos.notification.Context;
import com.yumu.eventsapiserv.pojos.notification.Context.Type;
import com.yumu.eventsapiserv.pojos.notification.UserNotification;
import com.yumu.eventsapiserv.pojos.user.Friendship;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.UserNotificationRepository;
import com.yumu.eventsapiserv.repositories.UserRepository;
import com.yumu.eventsapiserv.tasks.TaskGenerator;



/*
 * TODO: bad name: This is not a notifications manager rather a 
 * manager that takes care of membership and friendship
 */
@Component
public class UserNotificationsManager {

	@Autowired
	private UserNotificationRepository notificationsRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserActivitiesRelationshipManager userActivityManager;
	
	@Autowired
	private FriendshipManager friendshipMgr;
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private TaskGenerator taskQ;
	
	public void sendMembershipRequestToActivityOwners(Activity activity, String sender) {
		List<String> ownerIds = activity.getOwners();
		if(ownerIds==null){
			//TODO: log
			return;
		}

		List<YumuUser> owners = this.userRepo.findByIdIn(ownerIds);
		
		YumuUser uSender = this.userManager.findById(sender);
		
		String title = "";
		if(StringUtils.isNotBlank(uSender.getNameInfo().getScreenName())){
			title = uSender.getNameInfo().getScreenName() + " sent a membership request for " + activity.getName();
		} else {
			title = "Activity membership request received for "+ activity.getName();
		}
		if(title.length()>128){
			title = title.substring(0, 127);
		}
		
		for (YumuUser owner: owners) {
			UserNotification un = new UserNotification();
			un.setSender(sender);
			un.setReceiver(owner.getId());
			un.setTitle(title);
			un.setStatus(UserNotification.Status.OPEN);
			un.setType(UserNotification.Type.MEMBER_REQUEST);
			un.setCreatedAt(new DateTime(DateTimeZone.UTC));
			un.setUpdatedAt(new DateTime(DateTimeZone.UTC));
			Context ctx = new Context();
			ctx.setContextId(activity.getId());
			ctx.setType(Type.ACTIVITY);
			un.setContext(ctx);
			notificationsRepo.save(un);
			
			/*
			 * generate GCM task and send it
			 */
			this.taskQ.generateMembershipRequestNotification(activity.getId(), owner.getId(), title);

		}
	}

	public void sendFriendRequestNotification(String yourId, String theirId) {
		UserNotification un = new UserNotification();
		un.setSender(yourId);
		un.setReceiver(theirId);
		
		YumuUser user = this.userManager.findById(yourId);
		String title = "";
		if(StringUtils.isNotBlank(user.getNameInfo().getScreenName())){
			user.getNameInfo().getScreenName();
			title = user.getNameInfo().getScreenName() + " sent a friend request";
		} else {
			title = "You received a friend request";
		}
		if(title.length()>128){
			title = title.substring(0, 127);
		}

		un.setTitle(title);
		un.setStatus(UserNotification.Status.OPEN);
		un.setType(UserNotification.Type.BEFRIEND);
		un.setCreatedAt(new DateTime(DateTimeZone.UTC));
		un.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		
		Context ctx = new Context();
		ctx.setContextId(yourId);
		ctx.setType(Type.FRIEND);
		un.setContext(ctx);
		notificationsRepo.save(un);
		
		/*
		 * generate GCM task and send it
		 */
		this.taskQ.generateFriendshipNotification(theirId, title);
	}

	public void sendMembershipAdditionToUser(Activity activity, String yourId, String theirId) {
		UserNotification un = new UserNotification();
		String title = "";
		
		YumuUser you = this.userManager.findById(yourId);
		if(StringUtils.isNotBlank(you.getNameInfo().getScreenName())){
			title = you.getNameInfo().getScreenName() + " added you to " + activity.getName();
		} else {
			title = "You have been added to " + activity.getName();
		}
		if(title.length()>128){
			title = title.substring(0, 127);
		}

		un.setSender(yourId);
		un.setReceiver(theirId);
		un.setTitle(title);
		un.setStatus(UserNotification.Status.OPEN);
		un.setType(UserNotification.Type.MEMBER_INVITE);
		un.setCreatedAt(new DateTime(DateTimeZone.UTC));
		un.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		Context ctx = new Context();
		ctx.setContextId(activity.getId());
		ctx.setType(Type.ACTIVITY);
		un.setContext(ctx);
		notificationsRepo.save(un);
		
		/*
		 * generate GCM task and send it
		 */
		this.taskQ.generateMembershipInvitationNotification(activity.getId(), theirId, title);

	}
	
	public void sendMembershipInvitationToUser(Activity activity, String yourId, String theirId) {
		UserNotification un = new UserNotification();
		
		String title = "";
		
		YumuUser you = this.userManager.findById(yourId);
		if(StringUtils.isNotBlank(you.getNameInfo().getScreenName())){
			title = you.getNameInfo().getScreenName() + " invited you to join " + activity.getName();
		} else {
			title = "You are invited you to join " + activity.getName();
		}
		if(title.length()>128){
			title = title.substring(0, 127);
		}

		un.setSender(yourId);
		un.setReceiver(theirId);
		un.setTitle(title);
		un.setStatus(UserNotification.Status.OPEN);
		un.setType(UserNotification.Type.MEMBER_INVITE);
		un.setCreatedAt(new DateTime(DateTimeZone.UTC));
		un.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		Context ctx = new Context();
		ctx.setContextId(activity.getId());
		ctx.setType(Type.ACTIVITY);
		un.setContext(ctx);
		notificationsRepo.save(un);
		
		/*
		 * generate GCM task and send it
		 */
		this.taskQ.generateMembershipInvitationNotification(activity.getId(), theirId, title);
	}

	
	private Page<UserNotification> decorateNotificationsWithUserDetails(Page<UserNotification> notifications){
		
		if(notifications==null){
			return null;
		}
		
		notifications.forEach(x -> {
			x.setSenderDetails(this.userManager.findById(x.getSender()));
			x.setReceiverDetails(this.userManager.findById(x.getReceiver()));
			Friendship fx = this.friendshipMgr.checkFriendLink(x.getSender(), x.getReceiver());
			if(fx!=null){
				x.setFriendship(fx.getStatus());
			}
		});
		return notifications;
	}
	
	public UserNotification getFriendRequestNotification(String yourId, String theirId){
		UserNotification x = notificationsRepo.findBySenderAndReceiverAndTypeAndStatus(yourId, theirId, 
				UserNotification.Type.BEFRIEND.name(), UserNotification.Status.OPEN.name());
		if(x!=null){
			x.setSenderDetails(this.userManager.findById(x.getSender()));
			x.setReceiverDetails(this.userManager.findById(x.getReceiver()));
			Friendship fx = this.friendshipMgr.checkFriendLink(x.getSender(), x.getReceiver());
			if(fx!=null){
				x.setFriendship(fx.getStatus());
			}
		}

		return x;
	}

	public Page<UserNotification> getNotificationsByReceiverAndStatus(String receiverId, String status, Pageable page) {
		return decorateNotificationsWithUserDetails( notificationsRepo.findByReceiverAndStatus(receiverId, status, page));
	}

	public Page<UserNotification> getNotificationsBySenderAndStatus(String receiverId, String status, Pageable page) {
		return decorateNotificationsWithUserDetails( notificationsRepo.findByReceiverAndStatus(receiverId, status, page));
	}

	public UserNotification getNotification(String sender, String receiver,
			UserNotification.Type type, Context.Type contextType, String contextId) {
		return this.notificationsRepo.findBySenderAndReceiverAndTypeAndContext(sender, receiver, 
				type.name(), contextType.name(), contextId);
	}

	private void approveOrAcceptMembership(UserNotification notification, 
			String userId)  throws ApiAccessException {
		/*
		 * This is a activity based notification.
		 */
		if(notification.getContext()==null || notification.getContext().getContextId()==null){
			throw new ApiAccessException(ErrorMessages.BAD_NOTIFICATION_CONTEXT, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST)  {
			/*
			 * Someone requested membership  
			 */
			String activityId = notification.getContext().getContextId();
			userActivityManager.approveMembership(activityId, userId, notification.getSender(), UserType.MEMBER);

		} else if(notification.getType() == UserNotification.Type.MEMBER_INVITE){
			/*
			 * Someone sent a membership invitation
			 */
			String activityId = notification.getContext().getContextId();
			userActivityManager.acceptMembership(activityId, userId);
		}
	}

	private void markNotification(UserNotification notification, UserNotification.Status status ) {
		notification.setStatus(status);
		notification.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		notificationsRepo.save(notification);
	}
	
	/*
	 * main entry point for any approval action
	 * userId = yourId
	 */
	public void approve(String userId, String notificationId) throws ApiAccessException {

		UserNotification notification = notificationsRepo.findById(notificationId).get();
		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST ||
				notification.getType() == UserNotification.Type.MEMBER_INVITE ) {
			
			approveOrAcceptMembership(notification, userId);
		}

		else if(notification.getType() == UserNotification.Type.BEFRIEND ) {
			acceptFriendRequest(notification, userId);
		}
		//if it comes here, then mark the notification as done
		markNotification(notification, UserNotification.Status.ACCEPT);
	}
	/*
	 * Same method as above except that notification object is already available and passed
	 */
	public void approve(String userId, UserNotification notification) throws ApiAccessException {

		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST ||
				notification.getType() == UserNotification.Type.MEMBER_INVITE ) {
			
			approveOrAcceptMembership(notification, userId);
		}

		else if(notification.getType() == UserNotification.Type.BEFRIEND ) {
			acceptFriendRequest(notification, userId);
		}
		//if it comes here, then mark the notification as done
		markNotification(notification, UserNotification.Status.ACCEPT);
	}
	
	

	private void denyMembership(UserNotification notification, 
			String userId)  throws ApiAccessException {
		/*
		 * This is a activity based notification.
		 */
		if(notification.getContext()==null || notification.getContext().getContextId()==null){
			throw new ApiAccessException(ErrorMessages.BAD_NOTIFICATION_CONTEXT, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST)  {
			/*
			 * Someone requested membership  
			 */
			String activityId = notification.getContext().getContextId();
			userActivityManager.denyMembership(activityId, userId, notification.getSender(), UserType.MEMBER);

		} else if(notification.getType() == UserNotification.Type.MEMBER_INVITE){
			/*
			 * Someone sent a membership invitation.
			 */
			String activityId = notification.getContext().getContextId();
			userActivityManager.declineMembership(activityId, userId);
		}
	}

	
	/*
	 * main entry point for any denial actions
	 */
	public void deny(String userId, String notificationId) throws ApiAccessException {

		UserNotification notification = notificationsRepo.findById(notificationId).get();
		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST ||
				notification.getType() == UserNotification.Type.MEMBER_INVITE ) {
			
			denyMembership(notification, userId);
		}

		else if(notification.getType() == UserNotification.Type.BEFRIEND ) {
			denyFriendRequest(notification, userId);
		}
		//if it comes here, then mark the notification as done
		markNotification(notification, UserNotification.Status.DECLINE);
	}
	public void deny(String userId, UserNotification notification) throws ApiAccessException {

		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST ||
				notification.getType() == UserNotification.Type.MEMBER_INVITE ) {
			
			denyMembership(notification, userId);
		}

		else if(notification.getType() == UserNotification.Type.BEFRIEND ) {
			denyFriendRequest(notification, userId);
		}
		//if it comes here, then mark the notification as done
		markNotification(notification, UserNotification.Status.DECLINE);
	}
	private void acceptFriendRequest(UserNotification notification, String userId) throws ApiAccessException {
		if(notification.getContext()==null || notification.getContext().getContextId()==null){
			throw new ApiAccessException(ErrorMessages.BAD_NOTIFICATION_CONTEXT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		friendshipMgr.acceptFriendRequest(userId, notification.getContext().getContextId());
	}
	
	private void denyFriendRequest(UserNotification notification, String userId) throws ApiAccessException {
		if(notification.getContext()==null || notification.getContext().getContextId()==null){
			throw new ApiAccessException(ErrorMessages.BAD_NOTIFICATION_CONTEXT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		friendshipMgr.denyFriendRequest(userId, notification.getContext().getContextId());
		
	}


	private void ignoreMembership(UserNotification notification, 
			String userId)  throws ApiAccessException {
		/*
		 * This is a activity based notification.
		 */
		if(notification.getContext()==null || notification.getContext().getContextId()==null){
			throw new ApiAccessException(ErrorMessages.BAD_NOTIFICATION_CONTEXT, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST)  {
			/*
			 * Someone requested membership  
			 */
			String activityId = notification.getContext().getContextId();
			userActivityManager.ignoreMembershipByOwner(activityId, userId, notification.getSender(), UserType.MEMBER);

		} else if(notification.getType() == UserNotification.Type.MEMBER_INVITE){
			/*
			 * Someone sent a membership invitation.
			 */
			String activityId = notification.getContext().getContextId();
			userActivityManager.ignoreMembershipByUser(activityId, userId);
		}
	}

	/*
	 * main entry point for any ignore actions
	 */
	public void ignore(String userId, String notificationId) throws ApiAccessException {

		UserNotification notification = notificationsRepo.findById(notificationId).get();
		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST ||
				notification.getType() == UserNotification.Type.MEMBER_INVITE ) {
			
			ignoreMembership(notification, userId);
		}

		else if(notification.getType() == UserNotification.Type.BEFRIEND ) {
			ignoreFriendRequest(notification, userId);
		}
		//if it comes here, then mark the notification as done
		markNotification(notification, UserNotification.Status.IGNORE);
	}
	public void ignore(String userId, UserNotification notification) throws ApiAccessException {

		if(notification.getType() == UserNotification.Type.MEMBER_REQUEST ||
				notification.getType() == UserNotification.Type.MEMBER_INVITE ) {
			
			ignoreMembership(notification, userId);
		}

		else if(notification.getType() == UserNotification.Type.BEFRIEND ) {
			ignoreFriendRequest(notification, userId);
		}
		//if it comes here, then mark the notification as done
		markNotification(notification, UserNotification.Status.IGNORE);
	}
	
	private void ignoreFriendRequest(UserNotification notification, String userId) throws ApiAccessException {
		if(notification.getContext()==null || notification.getContext().getContextId()==null){
			throw new ApiAccessException(ErrorMessages.BAD_NOTIFICATION_CONTEXT, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		friendshipMgr.ignoreFriendRequest(userId, notification.getContext().getContextId());
		
	}


	public void updateNotification(String sender, String receiver,
			UserNotification.Type type, Context.Type contextType, String contextId,
			UserNotification.Status status) {
		UserNotification notification =  this.notificationsRepo.findBySenderAndReceiverAndTypeAndContext(sender, receiver, 
				type.name(), contextType.name(), contextId);
		if(notification!=null){
			this.markNotification(notification, status);
		}
		
	}

	public Long getOpenNotificationsCountByUser(String receiverId) {
		return this.notificationsRepo.countByReceiverAndStatus(receiverId, "OPEN");
	}

}
