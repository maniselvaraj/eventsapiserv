
package com.yumu.eventsapiserv.pojos.search;

import java.util.HashMap;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "val"
})
public class Search {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("val")
    @JsonPropertyDescription("")
    @Valid
    private Object val;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    private Search.Type type;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The t
     */
    @JsonProperty("val")
    public Object getVal() {
        return val;
    }

    /**
     * 
     * (Required)
     * 
     * @param t
     *     The T
     */
    @JsonProperty("val")
    public void setVal(Object t) {
        this.val = t;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Search.Type getType() {
        return type;
    }

    /**
     * 
     * (Required)
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(Search.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(val).append(type).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Search) == false) {
            return false;
        }
        Search rhs = ((Search) other);
        return new EqualsBuilder().append(val, rhs.val).append(type, rhs.type).isEquals();
    }

    public enum Type {

        ACTIVITY("ACTIVITY"),
        POST("POST"),
        USER("USER");
        private final String value;
        private final static Map<String, Search.Type> CONSTANTS = new HashMap<String, Search.Type>();

        static {
            for (Search.Type c: values()) {
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
        public static Search.Type fromValue(String value) {
            Search.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
