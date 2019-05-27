
package com.yumu.eventsapiserv.pojos.user.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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
 * User's preferences
 * <p>
 * User's preferences
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "yumu_user_id",
    "choices",
    "created_at",
    "updated_at",
    "version"
})
public class Preferences {

    /**
     * id of this record
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * internal id of the user
     * 
     */
    @JsonProperty("yumu_user_id")
    @JsonPropertyDescription("")
    private String yumuUserId;
    /**
     * array of user preferences
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("choices")
    @JsonPropertyDescription("")
    @Valid
    private List<Choice> choices = new ArrayList<Choice>();
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
     * version of the preferences. can be used for adding more stuff and migrating existing records
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("")
    private Preferences.Version version;

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
     * 
     * @return
     *     The yumuUserId
     */
    @JsonProperty("yumu_user_id")
    public String getYumuUserId() {
        return yumuUserId;
    }

    /**
     * internal id of the user
     * 
     * @param yumuUserId
     *     The yumu_user_id
     */
    @JsonProperty("yumu_user_id")
    public void setYumuUserId(String yumuUserId) {
        this.yumuUserId = yumuUserId;
    }

    /**
     * array of user preferences
     * (Required)
     * 
     * @return
     *     The choices
     */
    @JsonProperty("choices")
    public List<Choice> getChoices() {
        return choices;
    }

    /**
     * array of user preferences
     * (Required)
     * 
     * @param choices
     *     The choices
     */
    @JsonProperty("choices")
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
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

    /**
     * version of the preferences. can be used for adding more stuff and migrating existing records
     * 
     * @return
     *     The version
     */
    @JsonProperty("version")
    public Preferences.Version getVersion() {
        return version;
    }

    /**
     * version of the preferences. can be used for adding more stuff and migrating existing records
     * 
     * @param version
     *     The version
     */
    @JsonProperty("version")
    public void setVersion(Preferences.Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(yumuUserId).append(choices).append(createdAt).append(updatedAt).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Preferences) == false) {
            return false;
        }
        Preferences rhs = ((Preferences) other);
        return new EqualsBuilder().append(id, rhs.id).append(yumuUserId, rhs.yumuUserId).append(choices, rhs.choices).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).append(version, rhs.version).isEquals();
    }

    public enum Version {

        _1_0("1.0");
        private final String value;
        private final static Map<String, Preferences.Version> CONSTANTS = new HashMap<String, Preferences.Version>();

        static {
            for (Preferences.Version c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Version(String value) {
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
        public static Preferences.Version fromValue(String value) {
            Preferences.Version constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
