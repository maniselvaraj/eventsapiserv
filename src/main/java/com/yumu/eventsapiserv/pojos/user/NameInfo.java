
package com.yumu.eventsapiserv.pojos.user;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * name details
 * <p>
 * name details
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "first_name",
    "last_name",
    "middle_name",
    "full_name",
    "screen_name"
})
public class NameInfo {

    /**
     * 
     * 
     */
    @JsonProperty("first_name")
    @JsonPropertyDescription("")
    private String firstName;
    /**
     * 
     * 
     */
    @JsonProperty("last_name")
    @JsonPropertyDescription("")
    private String lastName;
    /**
     * 
     * 
     */
    @JsonProperty("middle_name")
    @JsonPropertyDescription("")
    private String middleName;
    /**
     * 
     * 
     */
    @JsonProperty("full_name")
    @JsonPropertyDescription("")
    private String fullName;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("screen_name")
    @JsonPropertyDescription("")
    private String screenName;

    /**
     * 
     * 
     * @return
     *     The firstName
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * 
     * @param firstName
     *     The first_name
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * 
     * @return
     *     The lastName
     */
    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * 
     * @param lastName
     *     The last_name
     */
    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * 
     * @return
     *     The middleName
     */
    @JsonProperty("middle_name")
    public String getMiddleName() {
        return middleName;
    }

    /**
     * 
     * 
     * @param middleName
     *     The middle_name
     */
    @JsonProperty("middle_name")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * 
     * 
     * @return
     *     The fullName
     */
    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * 
     * @param fullName
     *     The full_name
     */
    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The screenName
     */
    @JsonProperty("screen_name")
    public String getScreenName() {
        return screenName;
    }

    /**
     * 
     * (Required)
     * 
     * @param screenName
     *     The screen_name
     */
    @JsonProperty("screen_name")
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(firstName).append(lastName).append(middleName).append(fullName).append(screenName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NameInfo) == false) {
            return false;
        }
        NameInfo rhs = ((NameInfo) other);
        return new EqualsBuilder().append(firstName, rhs.firstName).append(lastName, rhs.lastName).append(middleName, rhs.middleName).append(fullName, rhs.fullName).append(screenName, rhs.screenName).isEquals();
    }

}
