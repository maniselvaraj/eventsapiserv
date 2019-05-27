
package com.yumu.eventsapiserv.pojos.activities;

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
 * Activity member
 * <p>
 * activity member
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "yumu_user_id",
    "type"
})
public class Member {

    /**
     * yumu user id
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("yumu_user_id")
    @JsonPropertyDescription("")
    private String yumuUserId;
    /**
     * type of member
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    @JsonPropertyDescription("")
    private Member.Type type;

    /**
     * yumu user id
     * (Required)
     * 
     * @return
     *     The yumuUserId
     */
    @JsonProperty("yumu_user_id")
    public String getYumuUserId() {
        return yumuUserId;
    }

    /**
     * yumu user id
     * (Required)
     * 
     * @param yumuUserId
     *     The yumu_user_id
     */
    @JsonProperty("yumu_user_id")
    public void setYumuUserId(String yumuUserId) {
        this.yumuUserId = yumuUserId;
    }

    /**
     * type of member
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Member.Type getType() {
        return type;
    }

    /**
     * type of member
     * (Required)
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(Member.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(yumuUserId).append(type).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Member) == false) {
            return false;
        }
        Member rhs = ((Member) other);
        return new EqualsBuilder().append(yumuUserId, rhs.yumuUserId).append(type, rhs.type).isEquals();
    }

    public enum Type {

        OWNER("OWNER"),
        MEMBER("MEMBER");
        private final String value;
        private final static Map<String, Member.Type> CONSTANTS = new HashMap<String, Member.Type>();

        static {
            for (Member.Type c: values()) {
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
        public static Member.Type fromValue(String value) {
            Member.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
