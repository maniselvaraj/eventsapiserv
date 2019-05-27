
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
 * Activity User Relationship Map (mini)
 * <p>
 * Relationship between an activity and user. Subset of activity_user_link
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "user_type",
    "status",
    "created_at",
    "updated_at"
})
public class UserRelation {

    /**
     * user's relationship with this activity
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("user_type")
    @JsonPropertyDescription("")
    private UserRelation.UserType userType;
    /**
     * user's membership status
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("status")
    @JsonPropertyDescription("")
    private UserRelation.Status status;
    /**
     * Time when the link was created
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonIgnore
    private DateTime createdAt;
    /**
     * Time when the link was updated
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @JsonIgnore
    private DateTime updatedAt;

    /**
     * user's relationship with this activity
     * (Required)
     * 
     * @return
     *     The userType
     */
    @JsonProperty("user_type")
    public UserRelation.UserType getUserType() {
        return userType;
    }

    /**
     * user's relationship with this activity
     * (Required)
     * 
     * @param userType
     *     The user_type
     */
    @JsonProperty("user_type")
    public void setUserType(UserRelation.UserType userType) {
        this.userType = userType;
    }

    /**
     * user's membership status
     * (Required)
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public UserRelation.Status getStatus() {
        return status;
    }

    /**
     * user's membership status
     * (Required)
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(UserRelation.Status status) {
        this.status = status;
    }

    /**
     * Time when the link was created
     * (Required)
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    @JsonIgnore
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
    @JsonIgnore
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Time when the link was updated
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Time when the link was updated
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
        return new HashCodeBuilder().append(userType).append(status).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserRelation) == false) {
            return false;
        }
        UserRelation rhs = ((UserRelation) other);
        return new EqualsBuilder().append(userType, rhs.userType).append(status, rhs.status).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public enum Status {

        ACTIVE("ACTIVE"),
        PINNED("PINNED"),
        DENIED("DENIED"),
        DECLINED("DECLINED"),
        PENDING_OWNER_APPROVAL("PENDING_OWNER_APPROVAL"),
        PENDING_USER_ACCEPTANCE("PENDING_USER_ACCEPTANCE"),
        BLOCKED("BLOCKED"),
        IGNORED("IGNORED"),
        USER_LEFT("USER_LEFT"),
        ACTIVITY_DELETED("ACTIVITY_DELETED");
        private final String value;
        private final static Map<String, UserRelation.Status> CONSTANTS = new HashMap<String, UserRelation.Status>();

        static {
            for (UserRelation.Status c: values()) {
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
        public static UserRelation.Status fromValue(String value) {
            UserRelation.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum UserType {

        FOLLOWER("FOLLOWER"),
        MEMBER("MEMBER"),
        OWNER("OWNER");
        private final String value;
        private final static Map<String, UserRelation.UserType> CONSTANTS = new HashMap<String, UserRelation.UserType>();

        static {
            for (UserRelation.UserType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private UserType(String value) {
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
        public static UserRelation.UserType fromValue(String value) {
            UserRelation.UserType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
