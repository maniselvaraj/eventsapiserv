package com.yumu.eventsapiserv.utils;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.pojos.user.SocialAccount;
import com.yumu.eventsapiserv.pojos.user.SocialAccount.SignInProvider;
import com.yumu.eventsapiserv.pojos.user.YumuUser;

public class UserUtil {

	public static String getFacebookIdFromUser(YumuUser user){
		if(user==null || user.getSocialInfo()==null){
			return null;
		}
		
		List<SocialAccount> socials = user.getSocialInfo();
		for(SocialAccount account: socials){
			if(account.getSignInProvider() == SocialAccount.SignInProvider.FACEBOOK){
				return account.getUserId();
			}
		}
		return null;
	}
	
	public static YumuUser getFacebookInfo(YumuUser user) throws ApiAccessException{
		if(user==null || user.getSocialInfo()==null || user.getSocialInfo().size()==0){
			throw new ApiAccessException(ErrorMessages.BAD_SOCIAL_INFO, HttpStatus.BAD_REQUEST);
		}
		List<SocialAccount> socialInfo = user.getSocialInfo();
		
		for(SocialAccount s : socialInfo){
			if(s.getSignInProvider()==SignInProvider.FACEBOOK) {
				return user;
			}
		}
		return null;
	}
	
	public static String getFacebookProfileImageFromUser(YumuUser user){
		if(user==null || user.getSocialInfo()==null){
			return null;
		}
		
		List<SocialAccount> socials = user.getSocialInfo();
		for(SocialAccount account: socials){
			if(account.getSignInProvider() == SocialAccount.SignInProvider.FACEBOOK){
				return account.getProfileImage();
			}
		}
		return null;
	}
	
	public static boolean areFriends(YumuUser yours, YumuUser theirs){
		if(yours==null || theirs==null){
			return false;
		}
		boolean found = false;
		if(yours.getFriends()!=null && yours.getFriends().contains(theirs.getId())){
			found = true;
		}
		if(!found){
			if(theirs.getFriends()!=null && theirs.getFriends().contains(yours.getId())){
				found = true;
			}
		}
		return found;
	}
	
}
