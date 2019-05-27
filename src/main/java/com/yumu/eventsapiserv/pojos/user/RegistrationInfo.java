
package com.yumu.eventsapiserv.pojos.user;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
 * device registration details
 * <p>
 * device registration details
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "yumu_user_id",
    "token",
    "platform",
    "status",
    "created_at",
    "updated_at"
})
public class RegistrationInfo {


    /**
     * id of this record
     * ID is needed if you want to avoid duplicate record ccreation during save
     * ID servers as pk
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
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
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("yumu_user_id")
    @JsonPropertyDescription("")
    private String yumuUserId;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("token")
    @JsonPropertyDescription("")
    @Size(max = 2500)
    private String token;
    /**
     * user's membership status
     * 
     */
    @JsonProperty("platform")
    @JsonPropertyDescription("")
    private RegistrationInfo.Platform platform;
    /**
     * user's login status per device
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("")
    private RegistrationInfo.Status status;
    /**
     * Time when the event was created
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime createdAt;
    /**
     * Time when the event was last updated
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime updatedAt;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The yumuUserId
     */
    @JsonProperty("yumu_user_id")
    public String getYumuUserId() {
        return yumuUserId;
    }

    /**
     * 
     * (Required)
     * 
     * @param yumuUserId
     *     The yumu_user_id
     */
    @JsonProperty("yumu_user_id")
    public void setYumuUserId(String yumuUserId) {
        this.yumuUserId = yumuUserId;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The token
     */
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    /**
     * 
     * (Required)
     * 
     * @param token
     *     The token
     */
    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * user's membership status
     * 
     * @return
     *     The platform
     */
    @JsonProperty("platform")
    public RegistrationInfo.Platform getPlatform() {
        return platform;
    }

    /**
     * user's membership status
     * 
     * @param platform
     *     The platform
     */
    @JsonProperty("platform")
    public void setPlatform(RegistrationInfo.Platform platform) {
        this.platform = platform;
    }

    /**
     * user's login status per device
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public RegistrationInfo.Status getStatus() {
        return status;
    }

    /**
     * user's login status per device
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(RegistrationInfo.Status status) {
        this.status = status;
    }

    /**
     * Time when the event was created
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Time when the event was created
     * 
     * @param createdAt
     *     The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Time when the event was last updated
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Time when the event was last updated
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
        return new HashCodeBuilder().append(yumuUserId).append(token).append(platform).append(status).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof RegistrationInfo) == false) {
            return false;
        }
        RegistrationInfo rhs = ((RegistrationInfo) other);
        return new EqualsBuilder().append(yumuUserId, rhs.yumuUserId).append(token, rhs.token).append(platform, rhs.platform).append(status, rhs.status).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public enum Platform {

        _0(0),
        _1(1);
        private final Integer value;
        private final static Map<Integer, RegistrationInfo.Platform> CONSTANTS = new HashMap<Integer, RegistrationInfo.Platform>();

        static {
            for (RegistrationInfo.Platform c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Platform(Integer value) {
            this.value = value;
        }

        @JsonValue
        public Integer value() {
            return this.value;
        }

        @JsonCreator
        public static RegistrationInfo.Platform fromValue(Integer value) {
            RegistrationInfo.Platform constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException((value +""));
            } else {
                return constant;
            }
        }

    }

    public enum Status {

        LOGGED_IN("LOGGED_IN"),
        LOGGED_OUT("LOGGED_OUT");
        private final String value;
        private final static Map<String, RegistrationInfo.Status> CONSTANTS = new HashMap<String, RegistrationInfo.Status>();

        static {
            for (RegistrationInfo.Status c: values()) {
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
        public static RegistrationInfo.Status fromValue(String value) {
            RegistrationInfo.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
