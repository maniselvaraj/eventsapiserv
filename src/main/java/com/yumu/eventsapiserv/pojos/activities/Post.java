
package com.yumu.eventsapiserv.pojos.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yumu.eventsapiserv.pojos.common.Image;
import com.yumu.eventsapiserv.pojos.common.Metrics;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;


/**
 * Post Resource
 * <p>
 * Request/response payload for creating a post
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "status",
    "activity_id",
    "content",
    "hashtags",
    "metrics",
    "images",
    "access_type",
    "owner",
    "created_at",
    "updated_at"
})
public class Post {

    /**
     * encrypted id of this resource
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * 
     */
    @JsonProperty("status")
    private Post.Status status;
    /**
     * activity to which this post belongs to
     * 
     */
    @JsonProperty("activity_id")
    @JsonPropertyDescription("")
    private String activityId;
    /**
     * content
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("content")
    @JsonPropertyDescription("")
    private String content;
    /**
     * array of hashtags of this resource
     * 
     */
    @JsonProperty("hashtags")
    @JsonPropertyDescription("")
    @Valid
    private List<String> hashtags = new ArrayList<String>();
    /**
     * Element Metrics. Element is the base of activity and post
     * <p>
     * Metrics
     * 
     */
    @JsonProperty("metrics")
    @JsonPropertyDescription("")
    @Valid
    private Metrics metrics;
    /**
     * various images
     * 
     */
    @JsonProperty("images")
    @JsonPropertyDescription("")
    @Valid
    private List<Image> images = new ArrayList<Image>();
    /**
     * Accessibility of the event
     * 
     */
    @JsonProperty("access_type")
    @JsonPropertyDescription("")
    private Post.AccessType accessType;
    /**
     * creator of the post.
     * 
     */
    @JsonProperty("owner")
    @JsonPropertyDescription("")
    private String owner;
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
     * encrypted id of this resource
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * encrypted id of this resource
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public Post.Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(Post.Status status) {
        this.status = status;
    }

    /**
     * activity to which this post belongs to
     * 
     * @return
     *     The activityId
     */
    @JsonProperty("activity_id")
    public String getActivityId() {
        return activityId;
    }

    /**
     * activity to which this post belongs to
     * 
     * @param activityId
     *     The activity_id
     */
    @JsonProperty("activity_id")
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    /**
     * content
     * (Required)
     * 
     * @return
     *     The content
     */
    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    /**
     * content
     * (Required)
     * 
     * @param content
     *     The content
     */
    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * array of hashtags of this resource
     * 
     * @return
     *     The hashtags
     */
    @JsonProperty("hashtags")
    public List<String> getHashtags() {
        return hashtags;
    }

    /**
     * array of hashtags of this resource
     * 
     * @param hashtags
     *     The hashtags
     */
    @JsonProperty("hashtags")
    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     * Element Metrics. Element is the base of activity and post
     * <p>
     * Metrics
     * 
     * @return
     *     The metrics
     */
    @JsonProperty("metrics")
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * Element Metrics. Element is the base of activity and post
     * <p>
     * Metrics
     * 
     * @param metrics
     *     The metrics
     */
    @JsonProperty("metrics")
    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * various images
     * 
     * @return
     *     The images
     */
    @JsonProperty("images")
    public List<Image> getImages() {
        return images;
    }

    /**
     * various images
     * 
     * @param images
     *     The images
     */
    @JsonProperty("images")
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * Accessibility of the event
     * 
     * @return
     *     The accessType
     */
    @JsonProperty("access_type")
    public Post.AccessType getAccessType() {
        return accessType;
    }

    /**
     * Accessibility of the event
     * 
     * @param accessType
     *     The access_type
     */
    @JsonProperty("access_type")
    public void setAccessType(Post.AccessType accessType) {
        this.accessType = accessType;
    }

    /**
     * creator of the post.
     * 
     * @return
     *     The owner
     */
    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    /**
     * creator of the post.
     * 
     * @param owner
     *     The owner
     */
    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
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
        return new HashCodeBuilder().append(id).append(status).append(activityId).append(content).append(hashtags).append(metrics).append(images).append(accessType).append(owner).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Post) == false) {
            return false;
        }
        Post rhs = ((Post) other);
        return new EqualsBuilder().append(id, rhs.id).append(status, rhs.status).append(activityId, rhs.activityId).append(content, rhs.content).append(hashtags, rhs.hashtags).append(metrics, rhs.metrics).append(images, rhs.images).append(accessType, rhs.accessType).append(owner, rhs.owner).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public enum AccessType {

        PUBLIC("PUBLIC"),
        PRIVATE("PRIVATE");
        private final String value;
        private final static Map<String, Post.AccessType> CONSTANTS = new HashMap<String, Post.AccessType>();

        static {
            for (Post.AccessType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private AccessType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Post.AccessType fromValue(String value) {
            Post.AccessType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Status {

        ACTIVE("ACTIVE"),
        DELETED("DELETED"),
        DISABLED("DISABLED");
        private final String value;
        private final static Map<String, Post.Status> CONSTANTS = new HashMap<String, Post.Status>();

        static {
            for (Post.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Post.Status fromValue(String value) {
            Post.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
