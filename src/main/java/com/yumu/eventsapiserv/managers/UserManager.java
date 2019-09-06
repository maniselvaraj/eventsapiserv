/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.managers;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.yumu.eventsapiserv.exceptions.ApiAccessException;
import com.yumu.eventsapiserv.exceptions.ErrorMessages;
import com.yumu.eventsapiserv.pojos.common.Address;
import com.yumu.eventsapiserv.pojos.common.Phone;
import com.yumu.eventsapiserv.pojos.user.Friendship;
import com.yumu.eventsapiserv.pojos.user.NameInfo;
import com.yumu.eventsapiserv.pojos.user.UserMetrics;
import com.yumu.eventsapiserv.pojos.user.UserWithFriendship;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.repositories.UserRepository;



@Component
public class UserManager {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private FriendshipManager friendsManager;

	/*
	 * TODO: Circular dependency?
	 * http://www.baeldung.com/circular-dependencies-in-spring
	 */
	@Autowired
	private UserActivitiesRelationshipManager userActivityRelationMgr;

	public YumuUser checkIfExists(String id) throws ApiAccessException{
		Optional<YumuUser> user = this.userRepo.findById(id);
		if(user==null){
			throw new ApiAccessException("User not found: " + id, HttpStatus.NOT_FOUND);
		}
		return user.get();
	}


	public YumuUser findById(String id){
		return this.userRepo.findById(id).get();
	}

	public Page<YumuUser> findByIdIn(List<String> yuserIds, Pageable page){
		return this.userRepo.findByIdIn(yuserIds, page);
	}
	public List<YumuUser> findByIdIn(List<String> yuserIds){
		return this.userRepo.findByIdIn(yuserIds);
	}

	private void copyPhones(List<Phone> oldPhones, List<Phone> newPhones){
		newPhones.forEach(phone -> {
			boolean bFound = false;
			for (Phone p: oldPhones){
				if(p.getType() == phone.getType()){
					bFound=true;
					p.setNumber(phone.getNumber());
				}
			}
			if(!bFound){
				oldPhones.add(phone);
			}
		});
	}

	private void copyNames(YumuUser storedUser, YumuUser newUser ) {
		if(storedUser.getNameInfo()==null){
			storedUser.setNameInfo(new NameInfo());
		}
		NameInfo newName = newUser.getNameInfo();
		storedUser.getNameInfo().setFirstName(newName.getFirstName());
		storedUser.getNameInfo().setLastName(newName.getLastName());
		storedUser.getNameInfo().setFullName(newName.getFullName());
		storedUser.getNameInfo().setMiddleName(newName.getMiddleName());
		storedUser.getNameInfo().setScreenName(newName.getScreenName());
	}

	private void copyAddress(YumuUser storedUser, YumuUser newUser ) {

		if(storedUser.getAddress()==null){
			storedUser.setAddress(new Address()); 
		}

		storedUser.getAddress().setLine1(newUser.getAddress().getLine1());
		storedUser.getAddress().setLine2(newUser.getAddress().getLine2());
		storedUser.getAddress().setCity(newUser.getAddress().getCity());
		storedUser.getAddress().setPostalCode(newUser.getAddress().getPostalCode());
		storedUser.getAddress().setCountryCode(newUser.getAddress().getCountryCode());
	}


	public YumuUser updateUserProfile(String userId, YumuUser newUser) throws ApiAccessException {

		YumuUser storedUser =  userRepo.findById(userId).get();

		/*
		 * update the old data with newUser. You can only update certain things
		 */

		if(newUser.getEmail()!=null){
			/*
			 * check if the email is different and does not exist in yumu already
			 */
			if(!storedUser.getEmail().equalsIgnoreCase(newUser.getEmail())) {
				if(userRepo.findByEmail(newUser.getEmail())!=null){
					throw new ApiAccessException(ErrorMessages.USER_EMAIL_EXISTS, HttpStatus.ALREADY_REPORTED);
				}
			}
			/*
			 * if all is well, then
			 */
			storedUser.setEmail(newUser.getEmail());
		}

		if(newUser.getDob()!=null ) {
			if(storedUser.getDob()==null || !storedUser.getDob().equals(newUser.getDob())){
				storedUser.setDob(newUser.getDob());
			} 
		}
		
		if(newUser.getGender()!=null){
			if(storedUser.getGender()==null || !storedUser.getGender().equals(newUser.getGender())) {
				storedUser.setGender(newUser.getGender());
			}
		}

		if(newUser.getNameInfo() != null){
			copyNames(storedUser, newUser);
		}

		if(newUser.getPhones()!=null){
			copyPhones(storedUser.getPhones(), newUser.getPhones());
		}

		if(StringUtils.isNotBlank(newUser.getTagline()) && !StringUtils.equals(storedUser.getTagline(), newUser.getTagline())){
			storedUser.setTagline(newUser.getTagline());
		}

		if(newUser.getAddress()!=null ){
			copyAddress(storedUser, newUser );
		}

		/*
		 * TODO: need to validate that only one image can be primary in any type
		 */
		if(newUser.getImages().size() > 0){
			storedUser.setImages(newUser.getImages());
		}
		
		storedUser.setUpdatedAt(new DateTime(DateTimeZone.UTC));
		YumuUser updatedUser = userRepo.save(storedUser);
		
		this.loadMetrics(updatedUser);

		return updatedUser;

	}

	
	public void loadMetrics(YumuUser user){
		List<ActivityCount> activityMetrics = this.userActivityRelationMgr.getUserActivityMetrics(user.getId());
		/*
		 * TODO: Populate metrics on the fly. May be do it then and there.
		 */
		if(activityMetrics!=null){
			if(user.getMetrics()==null){
				user.setMetrics(new UserMetrics());
			}
			for(ActivityCount c: activityMetrics){
				if(c.getId().equals("OWNER")){
					user.getMetrics().setActivityOwner(c.getTotal());
				} 
				else if(c.getId().equals("MEMBER")){
					user.getMetrics().setActivityMember(c.getTotal());
				}
			}
		}
	}
	
	public YumuUser findByIdWithRelation(String id, Authentication apiActor) {
		if(apiActor==null){
			return this.findById(id);
		} else {
			UserDetails principal = (UserDetails) apiActor.getPrincipal();
			String apiActorId = principal.getUsername();

			//Find the user and load the relation with apiActor too
			YumuUser user = this.findById(id);
			
			this.loadMetrics(user);
			
			UserWithFriendship userEx = new UserWithFriendship();
			BeanUtils.copyProperties(user, userEx);
			Friendship link = this.friendsManager.checkFriendLink(apiActorId, id);
			if(link!=null){
				userEx.setFriendship(link.getStatus());
			}
			return userEx;
		}
	}
	
	public Page<YumuUser> getAllUsers(Pageable page){
		return this.userRepo.findAll(page);
	}


}
