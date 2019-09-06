package com.yumu.eventsapiserv.utils;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.pojos.activities.Activity;


public class ActivityValidator {
	
	public static void basicValidation(Activity activity) throws ApiAccessException {
		
		if(activity.getTimeInfo().getEndTime()==null){
			throw new ApiAccessException("Missing end time", HttpStatus.BAD_REQUEST);
		}
		
		DateTime endTime = activity.getTimeInfo().getEndTime();
		if(endTime.isBeforeNow()){
			throw new ApiAccessException("End time should be in the future", HttpStatus.BAD_REQUEST);
		}
		
	}

}
