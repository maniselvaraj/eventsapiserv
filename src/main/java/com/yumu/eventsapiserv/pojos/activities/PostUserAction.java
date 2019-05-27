
package com.yumu.eventsapiserv.pojos.activities;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;


/**
 * Post User Action Map
 * <p>
 * Maps of actions performed on a post
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "post_id",
    "yumu_user_id",
    "action",
    "created_at"
})
public class PostUserAction {

    /**
     * id of this record
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    @JsonIgnore
    private String id;
    /**
     * id of the post
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("post_id")
    @JsonPropertyDescription("")
    @JsonIgnore
    private String postId;
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
     * type of action
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("action")
    @JsonPropertyDescription("")
    private PostUserAction.Action action;
    /**
     * Time when the link was created
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime createdAt;

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
     * id of the post
     * (Required)
     * 
     * @return
     *     The postId
     */
    @JsonProperty("post_id")
    @JsonIgnore
    public String getPostId() {
        return postId;
    }

    /**
     * id of the post
     * (Required)
     * 
     * @param postId
     *     The post_id
     */
    @JsonProperty("post_id")
    @JsonIgnore
    public void setPostId(String postId) {
        this.postId = postId;
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
     * type of action
     * (Required)
     * 
     * @return
     *     The action
     */
    @JsonProperty("action")
    public PostUserAction.Action getAction() {
        return action;
    }

    /**
     * type of action
     * (Required)
     * 
     * @param action
     *     The action
     */
    @JsonProperty("action")
    public void setAction(PostUserAction.Action action) {
        this.action = action;
    }

    /**
     * Time when the link was created
     * (Required)
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Time when the link was created
     * (Required)
     * 
     * @param createdAt
     *     The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(postId).append(yumuUserId).append(action).append(createdAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof PostUserAction) == false) {
            return false;
        }
        PostUserAction rhs = ((PostUserAction) other);
        return new EqualsBuilder().append(id, rhs.id).append(postId, rhs.postId).append(yumuUserId, rhs.yumuUserId).append(action, rhs.action).append(createdAt, rhs.createdAt).isEquals();
    }

    public enum Action {

        LIKE("LIKE"),
        REPORT_ABUSE("REPORT_ABUSE");
        private final String value;
        private final static Map<String, PostUserAction.Action> CONSTANTS = new HashMap<String, PostUserAction.Action>();

        static {
            for (PostUserAction.Action c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Action(String value) {
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
        public static PostUserAction.Action fromValue(String value) {
            PostUserAction.Action constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
