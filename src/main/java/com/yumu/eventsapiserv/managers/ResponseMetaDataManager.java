package com.yumu.eventsapiserv.managers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ResponseMetaDataManager {

	@Autowired
	private UserNotificationsManager notificationsManager;


	public Map<String, String> getResponseMetadata(String userId){

		Map<String, String> value = new LinkedHashMap<>();

		try {

			if(StringUtils.isNotBlank(userId)) {
				Long notificationCount = this.notificationsManager.getOpenNotificationsCountByUser(userId);
				value.put("notifications_count", notificationCount.toString());
			}

		}catch(Exception e){
			//best effort
			System.out.println("response meta failed");
		}

		return value;
	}


}
