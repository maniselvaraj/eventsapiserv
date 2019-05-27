
package com.yumu.eventsapiserv.pojos.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yumu.eventsapiserv.pojos.common.Address;
import com.yumu.eventsapiserv.pojos.common.Image;
import com.yumu.eventsapiserv.pojos.common.Phone;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;


/**
 * User Resource
 * <p>
 * yumu user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "email",
    "dob",
    "gender",
    "username",
    "name_info",
    "address",
    "phones",
    "tagline",
    "images",
    "created_at",
    "updated_at",
    "role",
    "status",
    "social_info",
    "friends",
    "metrics"
})
public class YumuUser {

    /**
     * user id
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * email id. Should be unique.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("")
    private String email;
    /**
     * date of birth
     * 
     */
    @JsonProperty("dob")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime dob;
    /**
     * user's gender
     * 
     */
    @JsonProperty("gender")
    @JsonPropertyDescription("")
    private YumuUser.Gender gender;
    /**
     * yumu user name? Should be unique. TBD: is this needed? email should be user name
     * 
     */    
    @JsonProperty("username")
    @JsonPropertyDescription("")
    private String username;
    /**
     * name details
     * <p>
     * name details
     * 
     */
    @JsonProperty("name_info")
    @JsonPropertyDescription("")
    @Valid
    private NameInfo nameInfo;
    /**
     * postal address
     * <p>
     * postal address
     * 
     */
    @JsonProperty("address")
    @JsonPropertyDescription("")
    @Valid
    private Address address;
    /**
     * phone details
     * 
     */
    @JsonProperty("phones")
    @JsonPropertyDescription("")
    @Valid
    private List<Phone> phones = new ArrayList<Phone>();
    /**
     * 
     * 
     */
    @JsonProperty("tagline")
    @JsonPropertyDescription("")
    private String tagline;
    /**
     * user's various images
     * 
     */
    @JsonProperty("images")
    @JsonPropertyDescription("")
    @Valid
    private List<Image> images = new ArrayList<Image>();
    /**
     * Time when the event was created
     * (Required)
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime createdAt;
    /**
     * Time when the event was created
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime updatedAt;
    /**
     * array of user roles
     * 
     */
    @JsonProperty("role")
    @JsonPropertyDescription("")
    @Valid
    private List<Role> role = new ArrayList<Role>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("")
    private YumuUser.Status status;
    /**
     * user's social accounts
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("social_info")
    @JsonPropertyDescription("")
    @Size(min = 1, max = 1)
    @Valid
    private List<SocialAccount> socialInfo = new ArrayList<SocialAccount>();
    /**
     * list of friend's yumu user ids
     * 
     */
    @JsonProperty("friends")
    @JsonPropertyDescription("")
    @Valid
    private List<String> friends = new ArrayList<String>();
    /**
     * User Metrics
     * <p>
     * UserMetrics
     * 
     */
    @JsonProperty("metrics")
    @JsonPropertyDescription("")
    @Valid
    private UserMetrics metrics;

    /**
     * user id
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * user id
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * email id. Should be unique.
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * email id. Should be unique.
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * date of birth
     * 
     * @return
     *     The dob
     */
    @JsonProperty("dob")
    public DateTime getDob() {
        return dob;
    }

    /**
     * date of birth
     * 
     * @param dob
     *     The dob
     */
    @JsonProperty("dob")
    public void setDob(DateTime dob) {
        this.dob = dob;
    }

    /**
     * user's gender
     * 
     * @return
     *     The gender
     */
    @JsonProperty("gender")
    public YumuUser.Gender getGender() {
        return gender;
    }

    /**
     * user's gender
     * 
     * @param gender
     *     The gender
     */
    @JsonProperty("gender")
    public void setGender(YumuUser.Gender gender) {
        this.gender = gender;
    }

    /**
     * yumu user name? Should be unique. TBD: is this needed? email should be user name
     * 
     * @return
     *     The username
     */
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    /**
     * yumu user name? Should be unique. TBD: is this needed? email should be user name
     * 
     * @param username
     *     The username
     */
    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * name details
     * <p>
     * name details
     * 
     * @return
     *     The nameInfo
     */
    @JsonProperty("name_info")
    public NameInfo getNameInfo() {
        return nameInfo;
    }

    /**
     * name details
     * <p>
     * name details
     * 
     * @param nameInfo
     *     The name_info
     */
    @JsonProperty("name_info")
    public void setNameInfo(NameInfo nameInfo) {
        this.nameInfo = nameInfo;
    }

    /**
     * postal address
     * <p>
     * postal address
     * 
     * @return
     *     The address
     */
    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    /**
     * postal address
     * <p>
     * postal address
     * 
     * @param address
     *     The address
     */
    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * phone details
     * 
     * @return
     *     The phones
     */
    @JsonProperty("phones")
    public List<Phone> getPhones() {
        return phones;
    }

    /**
     * phone details
     * 
     * @param phones
     *     The phones
     */
    @JsonProperty("phones")
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    /**
     * 
     * 
     * @return
     *     The tagline
     */
    @JsonProperty("tagline")
    public String getTagline() {
        return tagline;
    }

    /**
     * 
     * 
     * @param tagline
     *     The tagline
     */
    @JsonProperty("tagline")
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    /**
     * user's various images
     * 
     * @return
     *     The images
     */
    @JsonProperty("images")
    public List<Image> getImages() {
        return images;
    }

    /**
     * user's various images
     * 
     * @param images
     *     The images
     */
    @JsonProperty("images")
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * Time when the event was created
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
     * Time when the event was created
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
     * Time when the event was created
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Time when the event was created
     * 
     * @param updatedAt
     *     The updated_at
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * array of user roles
     * 
     * @return
     *     The role
     */
    @JsonProperty("role")
    public List<Role> getRole() {
        return role;
    }

    /**
     * array of user roles
     * 
     * @param role
     *     The role
     */
    @JsonProperty("role")
    public void setRole(List<Role> role) {
        this.role = role;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public YumuUser.Status getStatus() {
        return status;
    }

    /**
     * 
     * (Required)
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(YumuUser.Status status) {
        this.status = status;
    }

    /**
     * user's social accounts
     * (Required)
     * 
     * @return
     *     The socialInfo
     */
    @JsonProperty("social_info")
    public List<SocialAccount> getSocialInfo() {
        return socialInfo;
    }

    /**
     * user's social accounts
     * (Required)
     * 
     * @param socialInfo
     *     The social_info
     */
    @JsonProperty("social_info")
    public void setSocialInfo(List<SocialAccount> socialInfo) {
        this.socialInfo = socialInfo;
    }

    /**
     * list of friend's yumu user ids
     * 
     * @return
     *     The friends
     */
    @JsonProperty("friends")
    public List<String> getFriends() {
        return friends;
    }

    /**
     * list of friend's yumu user ids
     * 
     * @param friends
     *     The friends
     */
    @JsonProperty("friends")
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    /**
     * User Metrics
     * <p>
     * UserMetrics
     * 
     * @return
     *     The metrics
     */
    @JsonProperty("metrics")
    public UserMetrics getMetrics() {
        return metrics;
    }

    /**
     * User Metrics
     * <p>
     * UserMetrics
     * 
     * @param metrics
     *     The metrics
     */
    @JsonProperty("metrics")
    public void setMetrics(UserMetrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(email).append(dob).append(gender).append(username).append(nameInfo).append(address).append(phones).append(tagline).append(images).append(createdAt).append(updatedAt).append(role).append(status).append(socialInfo).append(friends).append(metrics).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof YumuUser) == false) {
            return false;
        }
        YumuUser rhs = ((YumuUser) other);
        return new EqualsBuilder().append(id, rhs.id).append(email, rhs.email).append(dob, rhs.dob).append(gender, rhs.gender).append(username, rhs.username).append(nameInfo, rhs.nameInfo).append(address, rhs.address).append(phones, rhs.phones).append(tagline, rhs.tagline).append(images, rhs.images).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).append(role, rhs.role).append(status, rhs.status).append(socialInfo, rhs.socialInfo).append(friends, rhs.friends).append(metrics, rhs.metrics).isEquals();
    }


    public enum Gender {

        FEMALE("FEMALE"),
        MALE("MALE");
        private final String value;
        private final static Map<String, YumuUser.Gender> CONSTANTS = new HashMap<String, YumuUser.Gender>();

        static {
            for (YumuUser.Gender c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Gender(String value) {
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
        public static YumuUser.Gender fromValue(String value) {
            YumuUser.Gender constant = CONSTANTS.get(value);
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
        private final static Map<String, YumuUser.Status> CONSTANTS = new HashMap<String, YumuUser.Status>();

        static {
            for (YumuUser.Status c: values()) {
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
        public static YumuUser.Status fromValue(String value) {
            YumuUser.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
