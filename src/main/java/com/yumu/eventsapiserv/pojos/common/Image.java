
package com.yumu.eventsapiserv.pojos.common;

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
 * image object
 * <p>
 * image object
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "primary",
    "src"
})
public class Image {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    private Image.Type type;
    /**
     * marks image as primary image
     * 
     */
    @JsonProperty("primary")
    @JsonPropertyDescription("")
    private Boolean primary;
    /**
     * uri of the image
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("src")
    @JsonPropertyDescription("")
    private String src;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Image.Type getType() {
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
    public void setType(Image.Type type) {
        this.type = type;
    }

    /**
     * marks image as primary image
     * 
     * @return
     *     The primary
     */
    @JsonProperty("primary")
    public Boolean getPrimary() {
        return primary;
    }

    /**
     * marks image as primary image
     * 
     * @param primary
     *     The primary
     */
    @JsonProperty("primary")
    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    /**
     * uri of the image
     * (Required)
     * 
     * @return
     *     The src
     */
    @JsonProperty("src")
    public String getSrc() {
        return src;
    }

    /**
     * uri of the image
     * (Required)
     * 
     * @param src
     *     The src
     */
    @JsonProperty("src")
    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(primary).append(src).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Image) == false) {
            return false;
        }
        Image rhs = ((Image) other);
        return new EqualsBuilder().append(type, rhs.type).append(primary, rhs.primary).append(src, rhs.src).isEquals();
    }

    public enum Type {

        PROFILE("PROFILE"),
        AVATAR("AVATAR"),
        PROFILE_BACKGROUND("PROFILE_BACKGROUND"),
        POST("POST"),
        ACTIVITY("ACTIVITY");
        private final String value;
        private final static Map<String, Image.Type> CONSTANTS = new HashMap<String, Image.Type>();

        static {
            for (Image.Type c: values()) {
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
        public static Image.Type fromValue(String value) {
            Image.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
