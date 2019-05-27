
package com.yumu.eventsapiserv.pojos.activities;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yumu.eventsapiserv.pojos.common.Location;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;


/**
 * Activity User Relationship Map
 * <p>
 * Relationship between an activity and user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "activity_id",
    "yumu_user_id",
    "user_relation",
    "user_actions",
    "location",
    "created_at",
    "updated_at"
})
public class ActivityUserLink {

    /**
     * id of this record
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    @JsonIgnore
    private String id;
    /**
     * id of the activity
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("activity_id")
    @JsonPropertyDescription("")
    @JsonIgnore
    private String activityId;
    /**
     * internal id of the user
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("yumu_user_id")
    @JsonPropertyDescription("")
    @JsonIgnore
    private String yumuUserId;
    /**
     * Activity User Relationship Map (mini)
     * <p>
     * Relationship between an activity and user. Subset of activity_user_link
     * 
     */
    @JsonProperty("user_relation")
    @JsonPropertyDescription("")
    @Valid
    private UserRelation userRelation;
    /**
     * List of actions that an user performs on an activity
     * 
     */
    @JsonProperty("user_actions")
    @JsonPropertyDescription("")
    @Valid
    private List<ActivityUserAction> userActions = new ArrayList<ActivityUserAction>();
    /**
     * Location Resource
     * <p>
     * event location
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("location")
    @JsonPropertyDescription("")
    @Valid
    private Location location;
    /**
     * Time when the post was created
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime createdAt;
    /**
     * Time when the post was updated
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime updatedAt;

    /**
     * id of this record
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    @JsonIgnore
    public String getId() {
        return id;
    }

    /**
     * id of this record
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    @JsonIgnore
    public void setId(String id) {
        this.id = id;
    }

    /**
     * id of the activity
     * (Required)
     * 
     * @return
     *     The activityId
     */
    @JsonProperty("activity_id")
    @JsonIgnore
    public String getActivityId() {
        return activityId;
    }

    /**
     * id of the activity
     * (Required)
     * 
     * @param activityId
     *     The activity_id
     */
    @JsonProperty("activity_id")
    @JsonIgnore
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    /**
     * internal id of the user
     * (Required)
     * 
     * @return
     *     The yumuUserId
     */
    @JsonProperty("yumu_user_id")
    @JsonIgnore
    public String getYumuUserId() {
        return yumuUserId;
    }

    /**
     * internal id of the user
     * (Required)
     * 
     * @param yumuUserId
     *     The yumu_user_id
     */
    @JsonProperty("yumu_user_id")
    @JsonIgnore
    public void setYumuUserId(String yumuUserId) {
        this.yumuUserId = yumuUserId;
    }

    /**
     * Activity User Relationship Map (mini)
     * <p>
     * Relationship between an activity and user. Subset of activity_user_link
     * 
     * @return
     *     The userRelation
     */
    @JsonProperty("user_relation")
    public UserRelation getUserRelation() {
        return userRelation;
    }

    /**
     * Activity User Relationship Map (mini)
     * <p>
     * Relationship between an activity and user. Subset of activity_user_link
     * 
     * @param userRelation
     *     The user_relation
     */
    @JsonProperty("user_relation")
    public void setUserRelation(UserRelation userRelation) {
        this.userRelation = userRelation;
    }

    /**
     * List of actions that an user performs on an activity
     * 
     * @return
     *     The userActions
     */
    @JsonProperty("user_actions")
    public List<ActivityUserAction> getUserActions() {
        return userActions;
    }

    /**
     * List of actions that an user performs on an activity
     * 
     * @param userActions
     *     The user_actions
     */
    @JsonProperty("user_actions")
    public void setUserActions(List<ActivityUserAction> userActions) {
        this.userActions = userActions;
    }

    /**
     * Location Resource
     * <p>
     * event location
     * (Required)
     * 
     * @return
     *     The location
     */
    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    /**
     * Location Resource
     * <p>
     * event location
     * (Required)
     * 
     * @param location
     *     The location
     */
    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Time when the post was created
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Time when the post was created
     * 
     * @param createdAt
     *     The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Time when the post was updated
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Time when the post was updated
     * 
     * @param updatedAt
     *     The updated_at
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(activityId).append(yumuUserId).append(userRelation).append(userActions).append(location).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ActivityUserLink) == false) {
            return false;
        }
        ActivityUserLink rhs = ((ActivityUserLink) other);
        return new EqualsBuilder().append(id, rhs.id).append(activityId, rhs.activityId).append(yumuUserId, rhs.yumuUserId).append(userRelation, rhs.userRelation).append(userActions, rhs.userActions).append(location, rhs.location).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

}
