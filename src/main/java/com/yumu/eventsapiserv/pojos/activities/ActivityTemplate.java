
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
 * Activity Resource Creation Template
 * <p>
 * Template used for creating an activity
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "acivity_type"
})
public class ActivityTemplate {

    /**
     * encrypted id of this resource
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * class of the activity that is being created
     * 
     */
    @JsonProperty("acivity_type")
    @JsonPropertyDescription("")
    private ActivityTemplate.AcivityType acivityType;

    /**
     * encrypted id of this resource
     * (Required)
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * encrypted id of this resource
     * (Required)
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * class of the activity that is being created
     * 
     * @return
     *     The acivityType
     */
    @JsonProperty("acivity_type")
    public ActivityTemplate.AcivityType getAcivityType() {
        return acivityType;
    }

    /**
     * class of the activity that is being created
     * 
     * @param acivityType
     *     The acivity_type
     */
    @JsonProperty("acivity_type")
    public void setAcivityType(ActivityTemplate.AcivityType acivityType) {
        this.acivityType = acivityType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(acivityType).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ActivityTemplate) == false) {
            return false;
        }
        ActivityTemplate rhs = ((ActivityTemplate) other);
        return new EqualsBuilder().append(id, rhs.id).append(acivityType, rhs.acivityType).isEquals();
    }

    public enum AcivityType {

        RECREATIONAL_SPORTS("RECREATIONAL_SPORTS"),
        PROFESSIONAL_SPORTS("PROFESSIONAL_SPORTS"),
        WEEKEND_ACTIVITY("WEEKEND_ACTIVITY"),
        DAILY_ACTIVITY("DAILY_ACTIVITY"),
        RELAXATION("RELAXATION");
        private final String value;
        private final static Map<String, ActivityTemplate.AcivityType> CONSTANTS = new HashMap<String, ActivityTemplate.AcivityType>();

        static {
            for (ActivityTemplate.AcivityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private AcivityType(String value) {
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
        public static ActivityTemplate.AcivityType fromValue(String value) {
            ActivityTemplate.AcivityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
