
package com.yumu.eventsapiserv.pojos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * postal address
 * <p>
 * postal address
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "line1",
    "line2",
    "city",
    "state",
    "country_code",
    "postal_code",
    "metro"
})
public class Address {

    @JsonProperty("line1")
    private String line1;
    @JsonProperty("line2")
    private String line2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("metro")
    private String metro;

    /**
     * 
     * @return
     *     The line1
     */
    @JsonProperty("line1")
    public String getLine1() {
        return line1;
    }

    /**
     * 
     * @param line1
     *     The line1
     */
    @JsonProperty("line1")
    public void setLine1(String line1) {
        this.line1 = line1;
    }

    /**
     * 
     * @return
     *     The line2
     */
    @JsonProperty("line2")
    public String getLine2() {
        return line2;
    }

    /**
     * 
     * @param line2
     *     The line2
     */
    @JsonProperty("line2")
    public void setLine2(String line2) {
        this.line2 = line2;
    }

    /**
     * 
     * @return
     *     The city
     */
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The countryCode
     */
    @JsonProperty("country_code")
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 
     * @param countryCode
     *     The country_code
     */
    @JsonProperty("country_code")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 
     * @return
     *     The postalCode
     */
    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * 
     * @param postalCode
     *     The postal_code
     */
    @JsonProperty("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 
     * @return
     *     The metro
     */
    @JsonProperty("metro")
    public String getMetro() {
        return metro;
    }

    /**
     * 
     * @param metro
     *     The metro
     */
    @JsonProperty("metro")
    public void setMetro(String metro) {
        this.metro = metro;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(line1).append(line2).append(city).append(state).append(countryCode).append(postalCode).append(metro).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address) == false) {
            return false;
        }
        Address rhs = ((Address) other);
        return new EqualsBuilder().append(line1, rhs.line1).append(line2, rhs.line2).append(city, rhs.city).append(state, rhs.state).append(countryCode, rhs.countryCode).append(postalCode, rhs.postalCode).append(metro, rhs.metro).isEquals();
    }

}
