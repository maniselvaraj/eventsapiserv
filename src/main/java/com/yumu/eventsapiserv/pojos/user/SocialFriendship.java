
package com.yumu.eventsapiserv.pojos.user;

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
 * User's friend links
 * <p>
 * User's social friend links
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "your_id",
    "their_social_id",
    "sign_in_provider",
    "status",
    "created_at",
    "updated_at"
})
public class SocialFriendship {

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
     * social id of other user
     * 
     */
    @JsonProperty("their_social_id")
    @JsonPropertyDescription("")
    private String theirSocialId;
    /**
     * FACEBOOK, TWITTER, GOOGLE+ etc. MVP supports FACEBOOK only
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("sign_in_provider")
    @JsonPropertyDescription("")
    private SocialFriendship.SignInProvider signInProvider;
    /**
     * user's membership status
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("")
    private SocialFriendship.Status status;
    /**
     * Time when the link was created
     * 
     */
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
     * social id of other user
     * 
     * @return
     *     The theirSocialId
     */
    @JsonProperty("their_social_id")
    public String getTheirSocialId() {
        return theirSocialId;
    }

    /**
     * social id of other user
     * 
     * @param theirSocialId
     *     The their_social_id
     */
    @JsonProperty("their_social_id")
    public void setTheirSocialId(String theirSocialId) {
        this.theirSocialId = theirSocialId;
    }

    /**
     * FACEBOOK, TWITTER, GOOGLE+ etc. MVP supports FACEBOOK only
     * (Required)
     * 
     * @return
     *     The signInProvider
     */
    @JsonProperty("sign_in_provider")
    public SocialFriendship.SignInProvider getSignInProvider() {
        return signInProvider;
    }

    /**
     * FACEBOOK, TWITTER, GOOGLE+ etc. MVP supports FACEBOOK only
     * (Required)
     * 
     * @param signInProvider
     *     The sign_in_provider
     */
    @JsonProperty("sign_in_provider")
    public void setSignInProvider(SocialFriendship.SignInProvider signInProvider) {
        this.signInProvider = signInProvider;
    }

    /**
     * user's membership status
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public SocialFriendship.Status getStatus() {
        return status;
    }

    /**
     * user's membership status
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(SocialFriendship.Status status) {
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
        return new HashCodeBuilder().append(id).append(yourId).append(theirSocialId).append(signInProvider).append(status).append(createdAt).append(updatedAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SocialFriendship) == false) {
            return false;
        }
        SocialFriendship rhs = ((SocialFriendship) other);
        return new EqualsBuilder().append(id, rhs.id).append(yourId, rhs.yourId).append(theirSocialId, rhs.theirSocialId).append(signInProvider, rhs.signInProvider).append(status, rhs.status).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).isEquals();
    }

    public enum SignInProvider {

        FACEBOOK("FACEBOOK");
        private final String value;
        private final static Map<String, SocialFriendship.SignInProvider> CONSTANTS = new HashMap<String, SocialFriendship.SignInProvider>();

        static {
            for (SocialFriendship.SignInProvider c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private SignInProvider(String value) {
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
        public static SocialFriendship.SignInProvider fromValue(String value) {
            SocialFriendship.SignInProvider constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Status {

        OPEN("OPEN"),
        PROCESSED("PROCESSED");
        private final String value;
        private final static Map<String, SocialFriendship.Status> CONSTANTS = new HashMap<String, SocialFriendship.Status>();

        static {
            for (SocialFriendship.Status c: values()) {
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
        public static SocialFriendship.Status fromValue(String value) {
            SocialFriendship.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
