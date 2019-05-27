
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
 * user image object
 * <p>
 * image object uploaded by user
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "uri",
    "method"
})
public class UserImage {

    /**
     * gridfs file id
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    @JsonProperty("type")
    private UserImage.Type type;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("method")
    private UserImage.Method method;

    /**
     * gridfs file id
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * gridfs file id
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public UserImage.Type getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(UserImage.Type type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The uri
     */
    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *     The uri
     */
    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 
     * @return
     *     The method
     */
    @JsonProperty("method")
    public UserImage.Method getMethod() {
        return method;
    }

    /**
     * 
     * @param method
     *     The method
     */
    @JsonProperty("method")
    public void setMethod(UserImage.Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(type).append(uri).append(method).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserImage) == false) {
            return false;
        }
        UserImage rhs = ((UserImage) other);
        return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).append(uri, rhs.uri).append(method, rhs.method).isEquals();
    }

    public enum Method {

        GET("GET"),
        POST("POST");
        private final String value;
        private final static Map<String, UserImage.Method> CONSTANTS = new HashMap<String, UserImage.Method>();

        static {
            for (UserImage.Method c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Method(String value) {
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
        public static UserImage.Method fromValue(String value) {
            UserImage.Method constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum Type {

        THUMBNAIL("THUMBNAIL"),
        STANDARD("STANDARD"),
        ORIGINAL("ORIGINAL");
        private final String value;
        private final static Map<String, UserImage.Type> CONSTANTS = new HashMap<String, UserImage.Type>();

        static {
            for (UserImage.Type c: values()) {
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
        public static UserImage.Type fromValue(String value) {
            UserImage.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
