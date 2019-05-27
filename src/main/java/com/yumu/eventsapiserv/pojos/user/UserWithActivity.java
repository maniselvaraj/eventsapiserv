/*
 * Copyright (c) 2015, 2016, Smirva Systems Private Limited. All rights reserved.
 */
package com.yumu.eventsapiserv.pojos.user;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yumu.eventsapiserv.pojos.activities.ActivityUserLink;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "email",
    "dob",
    "username",
    "name_info",
    "address",
    "phones",
    "tagline",
    "images",
    "created_at",
    "updated_at",
    "role",
    "status",
    "social_info",
    "friends",
    "metrics",
    "activity_relation",
    "friendship"
})
public class UserWithActivity extends YumuUser {
	

	/*
	 * ActivityUserLink is the relation between an activity and an user
	 */
    @JsonProperty("activity_relation")
    @JsonPropertyDescription("")
    @Valid
    private ActivityUserLink activityRelation;

    @JsonProperty("activity_relation")
	public ActivityUserLink getActivityRelation() {
		return activityRelation;
	}

    @JsonProperty("activity_relation")
	public void setActivityRelation(ActivityUserLink activityRelation) {
		this.activityRelation = activityRelation;
	}
    
    
    /*
     * friendship is the relation between the api calling user and this user.
     */
    @JsonProperty("friendship")
    private Friendship.Status friendship;

    @JsonProperty("friendship")
	public Friendship.Status getFriendship() {
		return friendship;
	}

    @JsonProperty("friendship")
	public void setFriendship(Friendship.Status friendship) {
		this.friendship = friendship;
	}
    
    
    
}
