
package com.yumu.eventsapiserv.pojos.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * list of social friends
 * <p>
 * social friend list
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sign_in_provider",
    "yumu_user_id",
    "friends"
})
public class SocialFriends {

    /**
     * FACEBOOK, TWITTER, GOOGLE+ etc. MVP supports FACEBOOK only
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("sign_in_provider")
    @JsonPropertyDescription("")
    private SocialFriends.SignInProvider signInProvider;
    /**
     * existing yumu_user_id
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("yumu_user_id")
    @JsonPropertyDescription("")
    private String yumuUserId;
    /**
     * list of facebook ids needed for relationship later
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("friends")
    @JsonPropertyDescription("")
    @Size(min = 1)
    @Valid
    private List<String> friends = new ArrayList<String>();

    /**
     * FACEBOOK, TWITTER, GOOGLE+ etc. MVP supports FACEBOOK only
     * (Required)
     * 
     * @return
     *     The signInProvider
     */
    @JsonProperty("sign_in_provider")
    public SocialFriends.SignInProvider getSignInProvider() {
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
    public void setSignInProvider(SocialFriends.SignInProvider signInProvider) {
        this.signInProvider = signInProvider;
    }

    /**
     * existing yumu_user_id
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
     * existing yumu_user_id
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
     * list of facebook ids needed for relationship later
     * (Required)
     * 
     * @return
     *     The friends
     */
    @JsonProperty("friends")
    public List<String> getFriends() {
        return friends;
    }

    /**
     * list of facebook ids needed for relationship later
     * (Required)
     * 
     * @param friends
     *     The friends
     */
    @JsonProperty("friends")
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(signInProvider).append(yumuUserId).append(friends).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SocialFriends) == false) {
            return false;
        }
        SocialFriends rhs = ((SocialFriends) other);
        return new EqualsBuilder().append(signInProvider, rhs.signInProvider).append(yumuUserId, rhs.yumuUserId).append(friends, rhs.friends).isEquals();
    }

    public enum SignInProvider {

        FACEBOOK("FACEBOOK");
        private final String value;
        private final static Map<String, SocialFriends.SignInProvider> CONSTANTS = new HashMap<String, SocialFriends.SignInProvider>();

        static {
            for (SocialFriends.SignInProvider c: values()) {
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
        public static SocialFriends.SignInProvider fromValue(String value) {
            SocialFriends.SignInProvider constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
