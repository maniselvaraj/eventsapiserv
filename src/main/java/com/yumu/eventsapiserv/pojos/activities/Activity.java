
package com.yumu.eventsapiserv.pojos.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yumu.eventsapiserv.pojos.common.Contact;
import com.yumu.eventsapiserv.pojos.common.Image;
import com.yumu.eventsapiserv.pojos.common.Location;
import com.yumu.eventsapiserv.pojos.common.Metrics;
import com.yumu.eventsapiserv.pojos.common.TimeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * Activity Resource
 * <p>
 * Request/response payload for creating an activity
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "hashtags",
    "name",
    "description",
    "status",
    "metrics",
    "images",
    "type",
    "access_type",
    "owners",
    "members",
    "time_info",
    "location",
    "contact"
})
public class Activity {

    /**
     * encrypted id of this resource
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * array of hashtags of this resource
     * 
     */
    @JsonProperty("hashtags")
    @JsonPropertyDescription("")
    @Valid
    private Set<String> hashtags = new HashSet<>();
    /**
     * name or title of this resource
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("name")
    @JsonPropertyDescription("")
    @Size(max = 64)
    private String name;
    /**
     * description
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("description")
    @JsonPropertyDescription("")
    @Size(max = 2048)
    private String description;
    /**
     * 
     */
    @JsonProperty("status")
    private Activity.Status status;
    /**
     * Element Metrics. Element is the base of activity and post
     * <p>
     * Metrics
     * 
     */
    @JsonProperty("metrics")
    @JsonPropertyDescription("")
    @Valid
    private Metrics metrics;
    /**
     * various images
     * 
     */
    @JsonProperty("images")
    @JsonPropertyDescription("")
    @Valid
    private List<Image> images = new ArrayList<Image>();
    /**
     * Type of the event
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    @JsonPropertyDescription("")
    private Activity.Type type;
    /**
     * Accessibility of the event
     * 
     */
    @JsonProperty("access_type")
    @JsonPropertyDescription("")
    private Activity.AccessType accessType;
    /**
     * creators of the event.
     * 
     */
    @JsonProperty("owners")
    @JsonPropertyDescription("")
    @Valid
    private List<String> owners = new ArrayList<String>();
    /**
     * all users associated with this activity
     * 
     */
    @JsonProperty("members")
    @JsonPropertyDescription("")
    @Valid
    private List<Member> members = new ArrayList<Member>();
    /**
     * Time Info
     * <p>
     * event time
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("time_info")
    @JsonPropertyDescription("")
    @Valid
    private TimeInfo timeInfo;
    /**
     * Location Resource
     * <p>
     * event location
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("location")
    @JsonPropertyDescription("")
    @Valid
    private Location location;
    /**
     * contact info
     * <p>
     * contact info
     * 
     */
    @JsonProperty("contact")
    @JsonPropertyDescription("")
    @Valid
    private Contact contact;

    /**
     * encrypted id of this resource
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * encrypted id of this resource
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * array of hashtags of this resource
     * 
     * @return
     *     The hashtags
     */
    @JsonProperty("hashtags")
    public Set<String> getHashtags() {
        return hashtags;
    }

    /**
     * array of hashtags of this resource
     * 
     * @param hashtags
     *     The hashtags
     */
    @JsonProperty("hashtags")
    public void setHashtags(Set<String> hashtags) {
        this.hashtags = hashtags;
    }

    /**
     * name or title of this resource
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
     * name or title of this resource
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
     * description
     * (Required)
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * description
     * (Required)
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public Activity.Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(Activity.Status status) {
        this.status = status;
    }

    /**
     * Element Metrics. Element is the base of activity and post
     * <p>
     * Metrics
     * 
     * @return
     *     The metrics
     */
    @JsonProperty("metrics")
    public Metrics getMetrics() {
        return metrics;
    }

    /**
     * Element Metrics. Element is the base of activity and post
     * <p>
     * Metrics
     * 
     * @param metrics
     *     The metrics
     */
    @JsonProperty("metrics")
    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    /**
     * various images
     * 
     * @return
     *     The images
     */
    @JsonProperty("images")
    public List<Image> getImages() {
        return images;
    }

    /**
     * various images
     * 
     * @param images
     *     The images
     */
    @JsonProperty("images")
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * Type of the event
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Activity.Type getType() {
        return type;
    }

    /**
     * Type of the event
     * (Required)
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(Activity.Type type) {
        this.type = type;
    }

    /**
     * Accessibility of the event
     * 
     * @return
     *     The accessType
     */
    @JsonProperty("access_type")
    public Activity.AccessType getAccessType() {
        return accessType;
    }

    /**
     * Accessibility of the event
     * 
     * @param accessType
     *     The access_type
     */
    @JsonProperty("access_type")
    public void setAccessType(Activity.AccessType accessType) {
        this.accessType = accessType;
    }

    /**
     * creators of the event.
     * 
     * @return
     *     The owners
     */
    @JsonProperty("owners")
    public List<String> getOwners() {
        return owners;
    }

    /**
     * creators of the event.
     * 
     * @param owners
     *     The owners
     */
    @JsonProperty("owners")
    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    /**
     * all users associated with this activity
     * 
     * @return
     *     The members
     */
    @JsonProperty("members")
    public List<Member> getMembers() {
        return members;
    }

    /**
     * all users associated with this activity
     * 
     * @param members
     *     The members
     */
    @JsonProperty("members")
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    /**
     * Time Info
     * <p>
     * event time
     * (Required)
     * 
     * @return
     *     The timeInfo
     */
    @JsonProperty("time_info")
    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    /**
     * Time Info
     * <p>
     * event time
     * (Required)
     * 
     * @param timeInfo
     *     The time_info
     */
    @JsonProperty("time_info")
    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    /**
     * Location Resource
     * <p>
     * event location
     * (Required)
     * 
     * @return
     *     The location
     */
    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    /**
     * Location Resource
     * <p>
     * event location
     * (Required)
     * 
     * @param location
     *     The location
     */
    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * contact info
     * <p>
     * contact info
     * 
     * @return
     *     The contact
     */
    @JsonProperty("contact")
    public Contact getContact() {
        return contact;
    }

    /**
     * contact info
     * <p>
     * contact info
     * 
     * @param contact
     *     The contact
     */
    @JsonProperty("contact")
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(hashtags).append(name).append(description).append(status).append(metrics).append(images).append(type).append(accessType).append(owners).append(members).append(timeInfo).append(location).append(contact).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Activity) == false) {
            return false;
        }
        Activity rhs = ((Activity) other);
        return new EqualsBuilder().append(id, rhs.id).append(hashtags, rhs.hashtags).append(name, rhs.name).append(description, rhs.description).append(status, rhs.status).append(metrics, rhs.metrics).append(images, rhs.images).append(type, rhs.type).append(accessType, rhs.accessType).append(owners, rhs.owners).append(members, rhs.members).append(timeInfo, rhs.timeInfo).append(location, rhs.location).append(contact, rhs.contact).isEquals();
    }

    public enum AccessType {

        PUBLIC("PUBLIC"),
        PRIVATE("PRIVATE");
        private final String value;
        private final static Map<String, Activity.AccessType> CONSTANTS = new HashMap<String, Activity.AccessType>();

        static {
            for (Activity.AccessType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private AccessType(String value) {
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
        public static Activity.AccessType fromValue(String value) {
            Activity.AccessType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Status {

        ACTIVE("ACTIVE"),
        EXPIRED("EXPIRED"),
        DELETED("DELETED"),
        DISABLED("DISABLED");
        private final String value;
        private final static Map<String, Activity.Status> CONSTANTS = new HashMap<String, Activity.Status>();

        static {
            for (Activity.Status c: values()) {
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
        public static Activity.Status fromValue(String value) {
            Activity.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Type {

        DEFAULT("DEFAULT"),
        SPORTS("SPORTS"),
        MUSIC("MUSIC"),
        TRAINING("TRAINING"),
        EDUCATION("EDUCATION");
        private final String value;
        private final static Map<String, Activity.Type> CONSTANTS = new HashMap<String, Activity.Type>();

        static {
            for (Activity.Type c: values()) {
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
        public static Activity.Type fromValue(String value) {
            Activity.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
