package com.yumu.eventsapiserv.pojos.activities;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yumu.eventsapiserv.pojos.user.YumuUser;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "hashtags",
    "name",
    "description",
    "status",
    "metrics",
    "images",
    "type",
    "access_type",
    "owners",
    "time_info",
    "location",
    "contact",
    "user_relation",
    "owners_info",
    "activity_relation"
})
public class ActivityDetail extends Activity {

	
    @JsonProperty("owners_info")
    @JsonPropertyDescription("")
    @Valid
    private List<YumuUser> ownersInfo = null;

    @JsonProperty("owners_info")
	public List<YumuUser> getOwnersInfo() {
		return ownersInfo;
	}

    @JsonProperty("owners_info")
	public void setOwnersInfo(List<YumuUser> ownersInfo) {
		this.ownersInfo = ownersInfo;
	}
    
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
	
}
