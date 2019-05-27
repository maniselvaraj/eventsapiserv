
package com.yumu.eventsapiserv.pojos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * User Metrics
 * <p>
 * UserMetrics
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "friends",
    "activity_owner",
    "activity_member",
    "posts",
    "activity_likes",
    "post_likes"
})
public class UserMetrics {

    /**
     * number of friends
     * 
     */
    @JsonProperty("friends")
    @JsonPropertyDescription("")
    private Integer friends;
    /**
     * number of activities created by this user
     * 
     */
    @JsonProperty("activity_owner")
    @JsonPropertyDescription("")
    private Integer activityOwner;
    /**
     * number of activities associated with this user
     * 
     */
    @JsonProperty("activity_member")
    @JsonPropertyDescription("")
    private Integer activityMember;
    /**
     * number of posts created by this user
     * 
     */
    @JsonProperty("posts")
    @JsonPropertyDescription("")
    private Integer posts;
    /**
     * number of activities liked by this user
     * 
     */
    @JsonProperty("activity_likes")
    @JsonPropertyDescription("")
    private Integer activityLikes;
    /**
     * number of posts liked by this user
     * 
     */
    @JsonProperty("post_likes")
    @JsonPropertyDescription("")
    private Integer postLikes;

    /**
     * number of friends
     * 
     * @return
     *     The friends
     */
    @JsonProperty("friends")
    public Integer getFriends() {
        return friends;
    }

    /**
     * number of friends
     * 
     * @param friends
     *     The friends
     */
    @JsonProperty("friends")
    public void setFriends(Integer friends) {
        this.friends = friends;
    }

    /**
     * number of activities created by this user
     * 
     * @return
     *     The activityOwner
     */
    @JsonProperty("activity_owner")
    public Integer getActivityOwner() {
        return activityOwner;
    }

    /**
     * number of activities created by this user
     * 
     * @param activityOwner
     *     The activity_owner
     */
    @JsonProperty("activity_owner")
    public void setActivityOwner(Integer activityOwner) {
        this.activityOwner = activityOwner;
    }

    /**
     * number of activities associated with this user
     * 
     * @return
     *     The activityMember
     */
    @JsonProperty("activity_member")
    public Integer getActivityMember() {
        return activityMember;
    }

    /**
     * number of activities associated with this user
     * 
     * @param activityMember
     *     The activity_member
     */
    @JsonProperty("activity_member")
    public void setActivityMember(Integer activityMember) {
        this.activityMember = activityMember;
    }

    /**
     * number of posts created by this user
     * 
     * @return
     *     The posts
     */
    @JsonProperty("posts")
    public Integer getPosts() {
        return posts;
    }

    /**
     * number of posts created by this user
     * 
     * @param posts
     *     The posts
     */
    @JsonProperty("posts")
    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    /**
     * number of activities liked by this user
     * 
     * @return
     *     The activityLikes
     */
    @JsonProperty("activity_likes")
    public Integer getActivityLikes() {
        return activityLikes;
    }

    /**
     * number of activities liked by this user
     * 
     * @param activityLikes
     *     The activity_likes
     */
    @JsonProperty("activity_likes")
    public void setActivityLikes(Integer activityLikes) {
        this.activityLikes = activityLikes;
    }

    /**
     * number of posts liked by this user
     * 
     * @return
     *     The postLikes
     */
    @JsonProperty("post_likes")
    public Integer getPostLikes() {
        return postLikes;
    }

    /**
     * number of posts liked by this user
     * 
     * @param postLikes
     *     The post_likes
     */
    @JsonProperty("post_likes")
    public void setPostLikes(Integer postLikes) {
        this.postLikes = postLikes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(friends).append(activityOwner).append(activityMember).append(posts).append(activityLikes).append(postLikes).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserMetrics) == false) {
            return false;
        }
        UserMetrics rhs = ((UserMetrics) other);
        return new EqualsBuilder().append(friends, rhs.friends).append(activityOwner, rhs.activityOwner).append(activityMember, rhs.activityMember).append(posts, rhs.posts).append(activityLikes, rhs.activityLikes).append(postLikes, rhs.postLikes).isEquals();
    }

}
