
package com.yumu.eventsapiserv.pojos.notification;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yumu.eventsapiserv.pojos.user.Friendship;
import com.yumu.eventsapiserv.pojos.user.YumuUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;


/**
 * Notification object between 2 users
 * <p>
 * Notification object that models a notification message between 2 users
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "title",
    "sender",
    "sender_details",
    "receiver",
    "receiver_details",
    "sender_receiver_friendship",
    "context",
    "type",
    "status",
    "created_at",
    "updated_at"
})
public class UserNotification {

    /**
     * id of this record
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * id of the activity
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("sender")
    @JsonPropertyDescription("")
    private String sender;
    /**
     * User Resource
     * <p>
     * yumu user
     * 
     */
    @JsonProperty("sender_details")
    @JsonPropertyDescription("")
    @Valid
    private YumuUser senderDetails;
    /**
     * internal id of the user
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("receiver")
    @JsonPropertyDescription("")
    private String receiver;
    /**
     * User Resource
     * <p>
     * yumu user
     * 
     */
    @JsonProperty("receiver_details")
    @JsonPropertyDescription("")
    @Valid
    private YumuUser receiverDetails;
    /**
     * title of the notification
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("title")
    @JsonPropertyDescription("")
    @Size(max = 128)
    private String title;

    
    @JsonProperty("sender_receiver_friendship")
    @JsonPropertyDescription("")
    @Valid
    private Friendship.Status friendship;
    
    /**
     * notification context
     * <p>
     * notification context
     * 
     */
    @JsonProperty("context")
    @JsonPropertyDescription("")
    @Valid
    private Context context;
    /**
     * user's relationship with this activity
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    @JsonPropertyDescription("")
    private UserNotification.Type type;
    /**
     * user's membership status
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("status")
    @JsonPropertyDescription("")
    private UserNotification.Status status;
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
     * Time when the link was created
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("")
    private DateTime updatedAt;

    /**
     * id of this record
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
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
    public void setId(String id) {
        this.id = id;
    }

    /**
     * id of the activity
     * (Required)
     * 
     * @return
     *     The sender
     */
    @JsonProperty("sender")
    public String getSender() {
        return sender;
    }

    /**
     * id of the activity
     * (Required)
     * 
     * @param sender
     *     The sender
     */
    @JsonProperty("sender")
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * User Resource
     * <p>
     * yumu user
     * 
     * @return
     *     The senderDetails
     */
    @JsonProperty("sender_details")
    public YumuUser getSenderDetails() {
        return senderDetails;
    }

    /**
     * User Resource
     * <p>
     * yumu user
     * 
     * @param senderDetails
     *     The sender_details
     */
    @JsonProperty("sender_details")
    public void setSenderDetails(YumuUser senderDetails) {
        this.senderDetails = senderDetails;
    }

    /**
     * internal id of the user
     * (Required)
     * 
     * @return
     *     The receiver
     */
    @JsonProperty("receiver")
    public String getReceiver() {
        return receiver;
    }

    /**
     * internal id of the user
     * (Required)
     * 
     * @param receiver
     *     The receiver
     */
    @JsonProperty("receiver")
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * User Resource
     * <p>
     * yumu user
     * 
     * @return
     *     The receiverDetails
     */
    @JsonProperty("receiver_details")
    public YumuUser getReceiverDetails() {
        return receiverDetails;
    }

    /**
     * User Resource
     * <p>
     * yumu user
     * 
     * @param receiverDetails
     *     The receiver_details
     */
    @JsonProperty("receiver_details")
    public void setReceiverDetails(YumuUser receiverDetails) {
        this.receiverDetails = receiverDetails;
    }
    /**
     * title of the notification
     * (Required)
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * title of the notification
     * (Required)
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * notification context
     * <p>
     * notification context
     * 
     * @return
     *     The context
     */
    @JsonProperty("context")
    public Context getContext() {
        return context;
    }

    /**
     * notification context
     * <p>
     * notification context
     * 
     * @param context
     *     The context
     */
    @JsonProperty("context")
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * user's relationship with this activity
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public UserNotification.Type getType() {
        return type;
    }

    /**
     * user's relationship with this activity
     * (Required)
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(UserNotification.Type type) {
        this.type = type;
    }

    /**
     * user's membership status
     * (Required)
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public UserNotification.Status getStatus() {
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
    public void setStatus(UserNotification.Status status) {
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
     * Time when the link was created
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Time when the link was created
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
        return new HashCodeBuilder().append(id).append(sender).append(senderDetails).append(receiver).append(receiverDetails).append(title).append(friendship).append(context).append(type).append(status).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserNotification) == false) {
            return false;
        }
        UserNotification rhs = ((UserNotification) other);
        return new EqualsBuilder().append(id, rhs.id).append(sender, rhs.sender).append(senderDetails, rhs.senderDetails).append(receiver, rhs.receiver).append(receiverDetails, rhs.receiverDetails).append(title, rhs.title).append(friendship, rhs.friendship).append(context, rhs.context).append(type, rhs.type).append(status, rhs.status).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public enum Status {

        OPEN("OPEN"),
        IGNORE("IGNORE"),
        DECLINE("DECLINE"),
        ACCEPT("ACCEPT");
        private final String value;
        private final static Map<String, UserNotification.Status> CONSTANTS = new HashMap<String, UserNotification.Status>();

        static {
            for (UserNotification.Status c: values()) {
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
        public static UserNotification.Status fromValue(String value) {
            UserNotification.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Type {

        BEFRIEND("BEFRIEND"),
        MEMBER_REQUEST("MEMBER_REQUEST"),
        MEMBER_INVITE("MEMBER_INVITE");
        private final String value;
        private final static Map<String, UserNotification.Type> CONSTANTS = new HashMap<String, UserNotification.Type>();

        static {
            for (UserNotification.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Type(String value) {
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
        public static UserNotification.Type fromValue(String value) {
            UserNotification.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @JsonProperty("sender_receiver_friendship")
	public Friendship.Status getFriendship() {
		return friendship;
	}
    
    @JsonProperty("sender_receiver_friendship")
	public void setFriendship(Friendship.Status friendship) {
		this.friendship = friendship;
	}

}
