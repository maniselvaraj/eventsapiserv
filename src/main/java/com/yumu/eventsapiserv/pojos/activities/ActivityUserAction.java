
package com.yumu.eventsapiserv.pojos.activities;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * Activity User Action Map
 * <p>
 * Maps of actions performed on an activity
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "action",
    "status",
    "created_at",
    "updated_at"
})
public class ActivityUserAction {

    /**
     * type of action
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("action")
    @JsonPropertyDescription("")
    private ActivityUserAction.Action action;
    /**
     * type of action
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("status")
    @JsonPropertyDescription("")
    private ActivityUserAction.Status status;
    /**
     * Time when the link was created
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    private DateTime createdAt;
    /**
     * Time when the link was updated
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("")
    private DateTime updatedAt;

    /**
     * type of action
     * (Required)
     * 
     * @return
     *     The action
     */
    @JsonProperty("action")
    public ActivityUserAction.Action getAction() {
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
    public void setAction(ActivityUserAction.Action action) {
        this.action = action;
    }

    /**
     * type of action
     * (Required)
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public ActivityUserAction.Status getStatus() {
        return status;
    }

    /**
     * type of action
     * (Required)
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(ActivityUserAction.Status status) {
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
        return new HashCodeBuilder().append(action).append(status).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ActivityUserAction) == false) {
            return false;
        }
        ActivityUserAction rhs = ((ActivityUserAction) other);
        return new EqualsBuilder().append(action, rhs.action).append(status, rhs.status).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public enum Action {

        LIKES("LIKES"),
        PINS("PINS"),
        REPORTED("REPORTED"),
        REQUEST_MEMBERSHIP("REQUEST_MEMBERSHIP"),
        MEMBER_INVITE_RECEIVED("MEMBER_INVITE_RECEIVED");
        private final String value;
        private final static Map<String, ActivityUserAction.Action> CONSTANTS = new HashMap<String, ActivityUserAction.Action>();

        static {
            for (ActivityUserAction.Action c: values()) {
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
        public static ActivityUserAction.Action fromValue(String value) {
            ActivityUserAction.Action constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Status {

        ENABLED("ENABLED"),
        DISABLED("DISABLED");
        private final String value;
        private final static Map<String, ActivityUserAction.Status> CONSTANTS = new HashMap<String, ActivityUserAction.Status>();

        static {
            for (ActivityUserAction.Status c: values()) {
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
        public static ActivityUserAction.Status fromValue(String value) {
            ActivityUserAction.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
