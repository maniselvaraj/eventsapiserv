/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.utils;


import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.yumu.eventsapiserv.pojos.activities.ActivityUserAction;
import com.yumu.eventsapiserv.pojos.activities.ActivityUserLink;


public class ActivityUserActionUtil {

//	public static ActivityUserAction createActivityUserAction(String actId, 
//			String userId, 
//			ActivityUserAction.Status status,
//			ActivityUserAction.Action action) {
//		
//		
//		ActivityUserAction actionlink = new ActivityUserAction();
//		actionlink.setActivityId(actId);
//		actionlink.setYumuUserId(userId);
//		actionlink.setStatus(status);
//		actionlink.setAction(action);
//		actionlink.setCreatedAt(new DateTime(DateTimeZone.UTC));
//		return actionlink;
//	}


	public static ActivityUserLink createActivityUserActionLink(String activityId,
			String userId, ActivityUserAction.Action action, 
			ActivityUserAction.Status status){
		
		ActivityUserLink link  = new ActivityUserLink();
		
		link.setActivityId(activityId);
		link.setYumuUserId(userId);
		
		link.setCreatedAt(new DateTime(DateTimeZone.UTC));
		link.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		
		List<ActivityUserAction> actions = new ArrayList<>();
		ActivityUserAction actionlink = new ActivityUserAction();
		actionlink.setStatus(status);
		actionlink.setAction(action);
		actionlink.setCreatedAt(new DateTime(DateTimeZone.UTC));
		actions.add(actionlink);
		link.setUserActions(actions);
		
		return link;
		
	}
}
