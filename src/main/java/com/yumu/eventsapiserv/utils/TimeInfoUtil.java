package com.yumu.eventsapiserv.utils;

import com.yumu.eventsapiserv.pojos.common.TimeInfo;

public class TimeInfoUtil {

	public static void compareAndCopy(TimeInfo newTime, TimeInfo oldTime){
		if(newTime!=null && oldTime!=null){
			if(newTime.getStartTime()!=null && !oldTime.getStartTime().equals(newTime.getStartTime())){
				oldTime.setStartTime(newTime.getStartTime());
			}
			if(newTime.getEndTime()!=null && !oldTime.getEndTime().equals(newTime.getEndTime())){
				oldTime.setEndTime(newTime.getEndTime());
			}
			if(newTime.getRepeatable()!=null && oldTime.getRepeatable().equals(newTime.getRepeatable())){
				oldTime.setRepeatable(newTime.getRepeatable());
			}
			if(newTime.getRepeats()!=null && !oldTime.getRepeats().equals(newTime.getRepeats())){
				oldTime.setRepeats(newTime.getRepeats());
			}
			if(newTime.getRepeatsEvery()!=null && !oldTime.getRepeatsEvery().equals(newTime.getRepeatsEvery())){
				oldTime.setRepeatsEvery(newTime.getRepeatsEvery());
			}
		}
	}

}
