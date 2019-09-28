package com.yumu.eventsapiserv.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yumu.eventsapiserv.managers.ActivityManager;
import com.yumu.eventsapiserv.managers.FriendshipManager;
import com.yumu.eventsapiserv.managers.SearchManager;
import com.yumu.eventsapiserv.pojos.activities.Activity;
import com.yumu.eventsapiserv.pojos.activities.ActivityDetail;
import com.yumu.eventsapiserv.pojos.user.Friendship;
import com.yumu.eventsapiserv.pojos.user.UserWithActivity;
import com.yumu.eventsapiserv.pojos.user.UserWithFriendship;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import com.yumu.eventsapiserv.utils.HttpResponseUtil;
import com.yumu.eventsapiserv.utils.UserAuthenticationUtils;



@RestController
@RequestMapping(path = Api.BASE_VERSION +  "/search")
public class SearchControllerImpl {

	// @Autowired
	// private MongoTemplate mongoTemplate;

	@Autowired
	private SearchManager manager;

	@Autowired
	private ActivityManager activityManager;

	@Autowired
	private FriendshipManager friendshipManager;

	/*
	 * How to search: db.activity.aggregate({$match: {$text: {$search:
	 * "yoga"}}}) db.post.aggregate({$match: {$text: {$search: "yoga"}}}) Ref:
	 * https://spring.io/blog/2014/07/17/text-search-your-documents-with-spring-data-mongodb
	 */

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> searchV1(@RequestParam(name = "q", required = true) String searchString,
			@RequestParam(name = "limit", required = false) String limit, Pageable page) {

		if (StringUtils.isBlank(searchString)) {
			return HttpResponseUtil.getJsonMessage("Invalid search string", HttpStatus.BAD_REQUEST);
		}

		String search = null;
		try {
			search = URLDecoder.decode(searchString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			search = searchString;
		}

		List<Activity> activities = null;
		// List<Post> posts = null;
		List<YumuUser> users = null;
		// long lTotalActivities = 0;
		// long lTotalPosts = 0;
		// long lTotalUsers = 0;

		// List<SearchResult> sResults = new LinkedList<>();

		if (StringUtils.isBlank(limit)) {
			activities = this.manager.searchTopActivity(search, 10);
			users = this.manager.searchTopUser(search, 10);
		} else if (StringUtils.equalsIgnoreCase(limit, "ACTIVITY")) {
			return seachActivity(search, page);
		} else if (StringUtils.equalsIgnoreCase(limit, "USER")) {
			return searchUser(search, page);
		}

		Map<String, Collection> searchResults = new HashMap<>();

		if (activities != null) {
			List<ActivityDetail> results = new ArrayList<>();
			activities.forEach(a -> {
				ActivityDetail d = this.activityManager.decorateActivityWithDetail(a);
				results.add(d);
			});
			searchResults.put("ACTIVITIES", results);
		}

		if (users != null) {
			List<UserWithFriendship> results = new ArrayList<>();
			users.forEach(u -> {
				UserWithFriendship u2 = this.friendshipManager.decorateWithFriendship(u);
				results.add(u2);
			});
			searchResults.put("USERS", results);
		}

		return new ResponseEntity<>(searchResults, HttpStatus.OK);
	}

	private ResponseEntity<?> searchUser(String search, Pageable page) {

		Page<YumuUser> users = this.manager.searchUser(search, page);

		List<Friendship> friends = null;
		Map<String, Friendship.Status> friendsMap = new LinkedHashMap<>();

		Authentication auth = UserAuthenticationUtils.getAuthenticatedUserNoException();
		if (auth != null) {
			UserDetails principal = (UserDetails) auth.getPrincipal();
			friends = this.friendshipManager.getFriendship(principal.getUsername());
			friends.forEach(friend -> {
				friendsMap.put(friend.getTheirId(), friend.getStatus());
			});

			List<UserWithActivity> userDetails = new ArrayList<>();

			users.forEach(_member -> {
				UserWithActivity _user = new UserWithActivity();
				BeanUtils.copyProperties(_member, _user);
				// _member.getSocialInfo().clear();
				_user.setFriendship(friendsMap.get(_member.getId()));
				userDetails.add(_user);
			});
			Page<UserWithActivity> usersWithRelationPage = new PageImpl<UserWithActivity>(userDetails, page,
					users.getTotalElements());
			return new ResponseEntity<>(usersWithRelationPage, HttpStatus.OK);
		}

		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	private ResponseEntity<?> seachActivity(String search, Pageable page) {
		Page<Activity> activities = this.manager.searchActivity(search, page);

		List<ActivityDetail> activityDetails = new ArrayList<>();
		/*
		 * Populate the relation between the user and activity
		 */
		activities.forEach(act -> {
			ActivityDetail detail = this.activityManager.decorateActivityWithDetail(act);
			activityDetails.add(detail);
		});

		Page<ActivityDetail> activityDetailsPage = new PageImpl<ActivityDetail>(activityDetails, page,
				activities.getTotalElements());

		return new ResponseEntity<>(activityDetailsPage, HttpStatus.OK);

	}

}
