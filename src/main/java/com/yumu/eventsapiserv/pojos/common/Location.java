
package com.yumu.eventsapiserv.pojos.common;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;


/**
 * Location Resource
 * <p>
 * event location
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "point",
    "address"
})
public class Location {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("point")
    @Valid
    @JsonDeserialize(using = GeoJsonDeserializer.class)
    private GeoJsonPoint point;
    /**
     * postal address
     * <p>
     * postal address
     * 
     */
    @JsonProperty("address")
    @Valid
    private Address address;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The point
     */
    @JsonProperty("point")
    public GeoJsonPoint getPoint() {
        return point;
    }

    /**
     * 
     * (Required)
     * 
     * @param point
     *     The point
     */
    @JsonProperty("point")
    public void setPoint(GeoJsonPoint point) {
        this.point = point;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(point).append(address).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Location) == false) {
            return false;
        }
        Location rhs = ((Location) other);
        return new EqualsBuilder().append(point, rhs.point).append(address, rhs.address).isEquals();
    }

}
