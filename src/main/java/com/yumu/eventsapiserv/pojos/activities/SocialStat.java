
package com.yumu.eventsapiserv.pojos.activities;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * social score object
 * <p>
 * social score object
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "count"
})
public class SocialStat {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    private SocialStat.Type type;
    /**
     * count of the above type
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("count")
    private Integer count;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public SocialStat.Type getType() {
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
    public void setType(SocialStat.Type type) {
        this.type = type;
    }

    /**
     * count of the above type
     * (Required)
     * 
     * @return
     *     The count
     */
    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    /**
     * count of the above type
     * (Required)
     * 
     * @param count
     *     The count
     */
    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(count).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SocialStat) == false) {
            return false;
        }
        SocialStat rhs = ((SocialStat) other);
        return new EqualsBuilder().append(type, rhs.type).append(count, rhs.count).isEquals();
    }

    public enum Type {

        UPVOTE("UPVOTE"),
        DOWNVOTE("DOWNVOTE"),
        PINS("PINS"),
        FRIENDS("FRIENDS"),
        MEMBERS("MEMBERS"),
        FOLLOWERS("FOLLOWERS");
        private final String value;
        private final static Map<String, SocialStat.Type> CONSTANTS = new HashMap<String, SocialStat.Type>();

        static {
            for (SocialStat.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Type(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static SocialStat.Type fromValue(String value) {
            SocialStat.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
