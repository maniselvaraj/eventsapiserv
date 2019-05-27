
package com.yumu.eventsapiserv.pojos.common;

import java.util.HashMap;
import java.util.Map;
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
 * phone
 * <p>
 * phone info
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "number"
})
public class Phone {

    @JsonProperty("type")
    private Phone.Type type;
    /**
     * entire phone number with country code etc
     * 
     */
    @JsonProperty("number")
    @JsonPropertyDescription("")
    private String number;

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Phone.Type getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(Phone.Type type) {
        this.type = type;
    }

    /**
     * entire phone number with country code etc
     * 
     * @return
     *     The number
     */
    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    /**
     * entire phone number with country code etc
     * 
     * @param number
     *     The number
     */
    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(number).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Phone) == false) {
            return false;
        }
        Phone rhs = ((Phone) other);
        return new EqualsBuilder().append(type, rhs.type).append(number, rhs.number).isEquals();
    }

    public enum Type {

        HOME("HOME"),
        MOBILE("MOBILE"),
        WORK("WORK");
        private final String value;
        private final static Map<String, Phone.Type> CONSTANTS = new HashMap<String, Phone.Type>();

        static {
            for (Phone.Type c: values()) {
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
        public static Phone.Type fromValue(String value) {
            Phone.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
