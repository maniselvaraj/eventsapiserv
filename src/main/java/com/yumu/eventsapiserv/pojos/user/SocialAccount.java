
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


/**
 * social account details
 * <p>
 * social account details
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sign_in_provider",
    "user_id",
    "email",
    "name",
    "profile_image"
})
public class SocialAccount {

    /**
     * FACEBOOK, TWITTER, GOOGLE+ etc. MVP supports FACEBOOK only
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("sign_in_provider")
    @JsonPropertyDescription("")
    private SocialAccount.SignInProvider signInProvider;
    /**
     * users unique id in social network. For example facebook id
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("user_id")
    @JsonPropertyDescription("")
    private String userId;
    /**
     * 
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("")
    private String email;
    /**
     * TBD: some name
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("name")
    @JsonPropertyDescription("")
    private String name;
    /**
     * users public facebook profile image. http url.
     * 
     */
    @JsonProperty("profile_image")
    @JsonPropertyDescription("")
    private String profileImage;

    /**
     * FACEBOOK, TWITTER, GOOGLE+ etc. MVP supports FACEBOOK only
     * (Required)
     * 
     * @return
     *     The signInProvider
     */
    @JsonProperty("sign_in_provider")
    public SocialAccount.SignInProvider getSignInProvider() {
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
    public void setSignInProvider(SocialAccount.SignInProvider signInProvider) {
        this.signInProvider = signInProvider;
    }

    /**
     * users unique id in social network. For example facebook id
     * (Required)
     * 
     * @return
     *     The userId
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * users unique id in social network. For example facebook id
     * (Required)
     * 
     * @param userId
     *     The user_id
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * TBD: some name
     * (Required)
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * TBD: some name
     * (Required)
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * users public facebook profile image. http url.
     * 
     * @return
     *     The profileImage
     */
    @JsonProperty("profile_image")
    public String getProfileImage() {
        return profileImage;
    }

    /**
     * users public facebook profile image. http url.
     * 
     * @param profileImage
     *     The profile_image
     */
    @JsonProperty("profile_image")
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(signInProvider).append(userId).append(email).append(name).append(profileImage).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SocialAccount) == false) {
            return false;
        }
        SocialAccount rhs = ((SocialAccount) other);
        return new EqualsBuilder().append(signInProvider, rhs.signInProvider).append(userId, rhs.userId).append(email, rhs.email).append(name, rhs.name).append(profileImage, rhs.profileImage).isEquals();
    }

    public enum SignInProvider {

        FACEBOOK("FACEBOOK");
        private final String value;
        private final static Map<String, SocialAccount.SignInProvider> CONSTANTS = new HashMap<String, SocialAccount.SignInProvider>();

        static {
            for (SocialAccount.SignInProvider c: values()) {
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
        public static SocialAccount.SignInProvider fromValue(String value) {
            SocialAccount.SignInProvider constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
