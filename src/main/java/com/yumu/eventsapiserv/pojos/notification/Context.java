
package com.yumu.eventsapiserv.pojos.notification;

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
 * notification context
 * <p>
 * notification context
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "context_id",
    "type"
})
public class Context {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("context_id")
    @JsonPropertyDescription("")
    private String contextId;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    private Context.Type type;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The contextId
     */
    @JsonProperty("context_id")
    public String getContextId() {
        return contextId;
    }

    /**
     * 
     * (Required)
     * 
     * @param contextId
     *     The context_id
     */
    @JsonProperty("context_id")
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Context.Type getType() {
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
    public void setType(Context.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(contextId).append(type).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Context) == false) {
            return false;
        }
        Context rhs = ((Context) other);
        return new EqualsBuilder().append(contextId, rhs.contextId).append(type, rhs.type).isEquals();
    }

    public enum Type {

        ACTIVITY("ACTIVITY"),
        POST("POST"),
        FRIEND("FRIEND");
        private final String value;
        private final static Map<String, Context.Type> CONSTANTS = new HashMap<String, Context.Type>();

        static {
            for (Context.Type c: values()) {
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
        public static Context.Type fromValue(String value) {
            Context.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
