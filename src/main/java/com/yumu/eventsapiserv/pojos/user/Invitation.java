
package com.yumu.eventsapiserv.pojos.user;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * Invitation for an activity
 * <p>
 * Object used to invite someone for an activity
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "your_id",
    "their_id",
    "status",
    "created_at",
    "updated_at"
})
public class Invitation {

    /**
     * id of this record
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * internal id of the user
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("your_id")
    @JsonPropertyDescription("")
    private String yourId;
    /**
     * internal id of other user
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("their_id")
    @JsonPropertyDescription("")
    private String theirId;

    /**
     * user's membership status
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("")
    private Invitation.Status status;
    /**
     * Time when the link was created
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime createdAt;
    /**
     * Time when the link was created
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
     * internal id of the user
     * (Required)
     * 
     * @return
     *     The yourId
     */
    @JsonProperty("your_id")
    public String getYourId() {
        return yourId;
    }

    /**
     * internal id of the user
     * (Required)
     * 
     * @param yourId
     *     The your_id
     */
    @JsonProperty("your_id")
    public void setYourId(String yourId) {
        this.yourId = yourId;
    }

    /**
     * internal id of other user
     * (Required)
     * 
     * @return
     *     The theirId
     */
    @JsonProperty("their_id")
    public String getTheirId() {
        return theirId;
    }

    /**
     * internal id of other user
     * (Required)
     * 
     * @param theirId
     *     The their_id
     */
    @JsonProperty("their_id")
    public void setTheirId(String theirId) {
        this.theirId = theirId;
    }

    /**
     * user's membership status
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public Invitation.Status getStatus() {
        return status;
    }

    /**
     * user's membership status
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(Invitation.Status status) {
        this.status = status;
    }

    /**
     * Time when the link was created
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
        return new HashCodeBuilder().append(id).append(yourId).append(theirId).append(status).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Invitation) == false) {
            return false;
        }
        Invitation rhs = ((Invitation) other);
        return new EqualsBuilder().append(id, rhs.id).append(yourId, rhs.yourId).append(theirId, rhs.theirId).append(status, rhs.status).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public enum Status {

        ACTIVE("ACTIVE"),
        PENDING("PENDING"),
        DECLINED("DECLINED"),
        IGNORED("IGNORED"),
        BLOCKED("BLOCKED");
        private final String value;
        private final static Map<String, Invitation.Status> CONSTANTS = new HashMap<String, Invitation.Status>();

        static {
            for (Invitation.Status c: values()) {
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
        public static Invitation.Status fromValue(String value) {
            Invitation.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
