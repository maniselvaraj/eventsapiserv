package com.yumu.eventsapiserv.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ActivityUtil {

	public static double MIN_SEARCH_RADIUS = 500;

	public static Set<String> extractHashTags(String text){

		Set<String> tags=new HashSet<>();
		if(StringUtils.isNotBlank(text)) {
			Pattern p = Pattern.compile("#(\\w+)");
			Matcher match = p.matcher(text);
			while (match.find()) {
				tags.add(match.group(1));
			}
		}
		return tags;
	}

	public static double getMinimumRadius(String r){

		double radius = MIN_SEARCH_RADIUS;
		try {
			radius = Double.valueOf(r);
		} catch(Exception e){
			return MIN_SEARCH_RADIUS;
		}
		if(radius < MIN_SEARCH_RADIUS){
			return MIN_SEARCH_RADIUS;
		}
		return radius;
	}


}
