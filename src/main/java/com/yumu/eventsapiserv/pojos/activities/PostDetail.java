package com.yumu.eventsapiserv.pojos.activities;


import java.util.Collection;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.yumu.eventsapiserv.pojos.user.YumuUser;

public class PostDetail extends Post {

	
    @JsonProperty("owner_info")
    @JsonPropertyDescription("")
    @Valid
    private YumuUser ownerInfo = null;

    @JsonProperty("owner_info")
	public YumuUser getOwnerInfo() {
		return ownerInfo;
	}

    @JsonProperty("owner_info")
	public void setOwnerInfo(YumuUser ownerInfo) {
		this.ownerInfo = ownerInfo;
	}
    
    
    /*
     * relation between post and user
     */
    @JsonProperty("post_relation")
    @JsonPropertyDescription("")
    @Valid
    private Collection<PostUserAction> postRelation;

    @JsonProperty("post_relation")
	public Collection<PostUserAction> getPostRelation() {
		return postRelation;
	}

    @JsonProperty("post_relation")
	public void setPostRelation(Collection<PostUserAction> postRelation) {
		this.postRelation = postRelation;
	}


    
    
}
