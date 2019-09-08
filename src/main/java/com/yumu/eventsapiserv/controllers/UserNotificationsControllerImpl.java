package com.yumu.eventsapiserv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.managers.UserNotificationsManager;
import com.yumu.eventsapiserv.pojos.notification.UserNotification;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;



@RestController
@RequestMapping(path = Api.BASE_VERSION + "/users/{userId}/notifications")
public class UserNotificationsControllerImpl {

	@Autowired
	private UserNotificationsManager notificationsManager;


	
	/*
	 * get an user's open notifications - incoming notifications
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getOpenInboxNotifications(@PathVariable("userId") String id, Pageable page)  {

		/*
		 * check if use is allowed to call this api
		 */
		try {
			Authentication auth = UserAuthenticationUtils.isUserAuthenticatedAndAllowed(id);

			Page<UserNotification> notifications = notificationsManager.
					getNotificationsByReceiverAndStatus(id, UserNotification.Status.OPEN.name(), page);

			return new ResponseEntity<>(notifications, HttpStatus.OK);
		} catch (ApiAccessException e) {
			return e.getHttpResponse();
		}
	}
	

	
	
	/*
	 * get an user's open notifications - outgoing notifications aka ones sent by user to somebody
	 */
	/*
	 * TODO: path is not good
	 */
	@RequestMapping(path = "/sent", method = RequestMethod.GET)
	public ResponseEntity<?> getOpenOutboxNotifications(@PathVariable("userId") String id, Pageable page)  {

		try {

			/*
			 * check if use is allowed to call this api
			 */
			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(id);

			Page<UserNotification> notifications = notificationsManager.
					getNotificationsBySenderAndStatus(id, UserNotification.Status.OPEN.name(), page);

			return new ResponseEntity<>(notifications, HttpStatus.OK);
		} catch (ApiAccessException e) {
			return e.getHttpResponse();
		}
	}

	@RequestMapping(path="/{notificationId}/approve", method=RequestMethod.POST)
	public ResponseEntity<?> approveRequest(@PathVariable("userId") String callingUserId, @PathVariable("notificationId") String notificationId) {
		try {
			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(callingUserId);
			
			notificationsManager.approve(callingUserId, notificationId);
			
			return HttpResponseUtil.getJsonMessage("Successfully approved notification " + notificationId, HttpStatus.OK);
			//return new ResponseEntity<>(null, HttpStatus.OK);
		} catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}
	
	@RequestMapping(path="/{notificationId}/deny", method=RequestMethod.POST)
	public ResponseEntity<?> denyRequest(@PathVariable("userId") String callingUserId, @PathVariable("notificationId") String notificationId) {
		try {
			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(callingUserId);
			
			notificationsManager.deny(callingUserId, notificationId);

			return HttpResponseUtil.getJsonMessage("Successfully denied notification " + notificationId, HttpStatus.OK);
			//return new ResponseEntity<>(null, HttpStatus.OK);
		} catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}

	@RequestMapping(path="/{notificationId}/ignore", method=RequestMethod.POST)
	public ResponseEntity<?> ignoreRequest(@PathVariable("userId") String callingUserId, @PathVariable("notificationId") String notificationId) {
		try {
			UserAuthenticationUtils.isUserAuthenticatedAndAllowed(callingUserId);
			
			notificationsManager.ignore(callingUserId, notificationId);
			
			//return new ResponseEntity<>(null, HttpStatus.OK);
			return HttpResponseUtil.getJsonMessage("Successfully ignored notification " + notificationId, HttpStatus.OK);
		} catch(ApiAccessException e){
			return e.getHttpResponse();
		}
	}
}
