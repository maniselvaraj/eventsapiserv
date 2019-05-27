package com.yumu.eventsapiserv.pojos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
    "friendship"
})
public class UserWithFriendship extends YumuUser {

	
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
