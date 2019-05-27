
package com.yumu.eventsapiserv.pojos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Element Metrics. Element is the base of activity and post
 * <p>
 * Metrics
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "visits",
    "likes",
    "pins",
    "members",
    "posts",
    "stars",
    "reported"
})
public class Metrics {

    /**
     * number of visits or GET requests
     * 
     */
    @JsonProperty("visits")
    @JsonPropertyDescription("")
    private Integer visits;
    /**
     * number of unique likes
     * 
     */
    @JsonProperty("likes")
    @JsonPropertyDescription("")
    private Integer likes;
    /**
     * number of times users have pinned this element
     * 
     */
    @JsonProperty("pins")
    @JsonPropertyDescription("")
    private Integer pins;
    /**
     * number of members
     * 
     */
    @JsonProperty("members")
    @JsonPropertyDescription("")
    private Integer members;
    /**
     * number of posts
     * 
     */
    @JsonProperty("posts")
    @JsonPropertyDescription("")
    private Integer posts;
    /**
     * five star rating average
     * 
     */
    @JsonProperty("stars")
    @JsonPropertyDescription("")
    private Double stars;
    /**
     * number of times users reported this activity as abusive
     * 
     */
    @JsonProperty("reported")
    @JsonPropertyDescription("")
    private Integer reported;

    /**
     * number of visits or GET requests
     * 
     * @return
     *     The visits
     */
    @JsonProperty("visits")
    public Integer getVisits() {
        return visits;
    }

    /**
     * number of visits or GET requests
     * 
     * @param visits
     *     The visits
     */
    @JsonProperty("visits")
    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    /**
     * number of unique likes
     * 
     * @return
     *     The likes
     */
    @JsonProperty("likes")
    public Integer getLikes() {
        return likes;
    }

    /**
     * number of unique likes
     * 
     * @param likes
     *     The likes
     */
    @JsonProperty("likes")
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * number of times users have pinned this element
     * 
     * @return
     *     The pins
     */
    @JsonProperty("pins")
    public Integer getPins() {
        return pins;
    }

    /**
     * number of times users have pinned this element
     * 
     * @param pins
     *     The pins
     */
    @JsonProperty("pins")
    public void setPins(Integer pins) {
        this.pins = pins;
    }

    /**
     * number of members
     * 
     * @return
     *     The members
     */
    @JsonProperty("members")
    public Integer getMembers() {
        return members;
    }

    /**
     * number of members
     * 
     * @param members
     *     The members
     */
    @JsonProperty("members")
    public void setMembers(Integer members) {
        this.members = members;
    }

    /**
     * number of posts
     * 
     * @return
     *     The posts
     */
    @JsonProperty("posts")
    public Integer getPosts() {
        return posts;
    }

    /**
     * number of posts
     * 
     * @param posts
     *     The posts
     */
    @JsonProperty("posts")
    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    /**
     * five star rating average
     * 
     * @return
     *     The stars
     */
    @JsonProperty("stars")
    public Double getStars() {
        return stars;
    }

    /**
     * five star rating average
     * 
     * @param stars
     *     The stars
     */
    @JsonProperty("stars")
    public void setStars(Double stars) {
        this.stars = stars;
    }

    /**
     * number of times users reported this activity as abusive
     * 
     * @return
     *     The reported
     */
    @JsonProperty("reported")
    public Integer getReported() {
        return reported;
    }

    /**
     * number of times users reported this activity as abusive
     * 
     * @param reported
     *     The reported
     */
    @JsonProperty("reported")
    public void setReported(Integer reported) {
        this.reported = reported;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(visits).append(likes).append(pins).append(members).append(posts).append(stars).append(reported).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Metrics) == false) {
            return false;
        }
        Metrics rhs = ((Metrics) other);
        return new EqualsBuilder().append(visits, rhs.visits).append(likes, rhs.likes).append(pins, rhs.pins).append(members, rhs.members).append(posts, rhs.posts).append(stars, rhs.stars).append(reported, rhs.reported).isEquals();
    }

}
