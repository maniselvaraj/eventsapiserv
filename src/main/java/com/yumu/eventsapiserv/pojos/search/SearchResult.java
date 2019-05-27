
package com.yumu.eventsapiserv.pojos.search;

import java.util.Collection;
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


/**
 * search result
 * <p>
 * search result - a single element
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
	"result"
})
public class SearchResult {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("result")
    @JsonPropertyDescription("")
    @Valid
    private Collection result;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    private SearchResult.Type type;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The result
     */
    @JsonProperty("result")
    public Collection getResult() {
        return result;
    }

    /**
     * 
     * (Required)
     * 
     * @param result
     *     The result
     */
    @JsonProperty("result")
    public void setResult(Collection result) {
        this.result = result;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public SearchResult.Type getType() {
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
    public void setType(SearchResult.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(result).append(type).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SearchResult) == false) {
            return false;
        }
        SearchResult rhs = ((SearchResult) other);
        return new EqualsBuilder().append(result, rhs.result).append(type, rhs.type).isEquals();
    }

    public enum Type {

        ACTIVITY("ACTIVITY"),
        POST("POST"),
        USER("USER");
        private final String value;
        private final static Map<String, SearchResult.Type> CONSTANTS = new HashMap<String, SearchResult.Type>();

        static {
            for (SearchResult.Type c: values()) {
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
        public static SearchResult.Type fromValue(String value) {
            SearchResult.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
