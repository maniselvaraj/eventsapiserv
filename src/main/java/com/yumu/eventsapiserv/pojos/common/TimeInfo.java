
package com.yumu.eventsapiserv.pojos.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;


/**
 * Time Info
 * <p>
 * event time
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "start_time",
    "end_time",
    "created_at",
    "updated_at",
    "repeatable",
    "repeats",
    "repeat_time",
    "repeats_on",
    "day_of_month",
    "repeats_every",
    "expired"
})
public class TimeInfo {

    /**
     * Time when the event starts. Used only for activity
     * 
     */
    @JsonProperty("start_time")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime startTime;
    /**
     * Time when the event expires. Used only for activity
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("end_time")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime endTime;
    /**
     * Time when the event was created
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime createdAt;
    /**
     * Time when the event was last updated
     * 
     */
    @JsonProperty("updated_at")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime updatedAt;
    /**
     * event is repeatable
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("repeatable")
    @JsonPropertyDescription("")
    private Boolean repeatable;
    /**
     * Used only for activity
     * 
     */
    @JsonProperty("repeats")
    @JsonPropertyDescription("")
    private TimeInfo.Repeats repeats;
    /**
     * Repeat time of day
     * 
     */
    @JsonProperty("repeat_time")
    @JsonPropertyDescription("")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private DateTime repeatTime;
    /**
     * repeat day of week
     * 
     */
    @JsonProperty("repeats_on")
    @JsonPropertyDescription("")
    @Valid
    private List<RepeatsOn> repeatsOn = new ArrayList<RepeatsOn>();
    /**
     * day of month for monthly repeat
     * 
     */
    @JsonProperty("day_of_month")
    @JsonPropertyDescription("")
    private Integer dayOfMonth;
    /**
     * Used only for activity
     * 
     */
    @JsonProperty("repeats_every")
    @JsonPropertyDescription("")
    private Integer repeatsEvery;
    /**
     * Flag used by batch to mark event as expired. For Search.
     * 
     */
    @JsonProperty("expired")
    @JsonPropertyDescription("")
    private Boolean expired = false;

    /**
     * Time when the event starts. Used only for activity
     * 
     * @return
     *     The startTime
     */
    @JsonProperty("start_time")
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * Time when the event starts. Used only for activity
     * 
     * @param startTime
     *     The start_time
     */
    @JsonProperty("start_time")
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Time when the event expires. Used only for activity
     * (Required)
     * 
     * @return
     *     The endTime
     */
    @JsonProperty("end_time")
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * Time when the event expires. Used only for activity
     * (Required)
     * 
     * @param endTime
     *     The end_time
     */
    @JsonProperty("end_time")
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Time when the event was created
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Time when the event was created
     * 
     * @param createdAt
     *     The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Time when the event was last updated
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Time when the event was last updated
     * 
     * @param updatedAt
     *     The updated_at
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * event is repeatable
     * (Required)
     * 
     * @return
     *     The repeatable
     */
    @JsonProperty("repeatable")
    public Boolean getRepeatable() {
        return repeatable;
    }

    /**
     * event is repeatable
     * (Required)
     * 
     * @param repeatable
     *     The repeatable
     */
    @JsonProperty("repeatable")
    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    /**
     * Used only for activity
     * 
     * @return
     *     The repeats
     */
    @JsonProperty("repeats")
    public TimeInfo.Repeats getRepeats() {
        return repeats;
    }

    /**
     * Used only for activity
     * 
     * @param repeats
     *     The repeats
     */
    @JsonProperty("repeats")
    public void setRepeats(TimeInfo.Repeats repeats) {
        this.repeats = repeats;
    }

    /**
     * Repeat time of day
     * 
     * @return
     *     The repeatTime
     */
    @JsonProperty("repeat_time")
    public DateTime getRepeatTime() {
        return repeatTime;
    }

    /**
     * Repeat time of day
     * 
     * @param repeatTime
     *     The repeat_time
     */
    @JsonProperty("repeat_time")
    public void setRepeatTime(DateTime repeatTime) {
        this.repeatTime = repeatTime;
    }

    /**
     * repeat day of week
     * 
     * @return
     *     The repeatsOn
     */
    @JsonProperty("repeats_on")
    public List<RepeatsOn> getRepeatsOn() {
        return repeatsOn;
    }

    /**
     * repeat day of week
     * 
     * @param repeatsOn
     *     The repeats_on
     */
    @JsonProperty("repeats_on")
    public void setRepeatsOn(List<RepeatsOn> repeatsOn) {
        this.repeatsOn = repeatsOn;
    }

    /**
     * day of month for monthly repeat
     * 
     * @return
     *     The dayOfMonth
     */
    @JsonProperty("day_of_month")
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * day of month for monthly repeat
     * 
     * @param dayOfMonth
     *     The day_of_month
     */
    @JsonProperty("day_of_month")
    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Used only for activity
     * 
     * @return
     *     The repeatsEvery
     */
    @JsonProperty("repeats_every")
    public Integer getRepeatsEvery() {
        return repeatsEvery;
    }

    /**
     * Used only for activity
     * 
     * @param repeatsEvery
     *     The repeats_every
     */
    @JsonProperty("repeats_every")
    public void setRepeatsEvery(Integer repeatsEvery) {
        this.repeatsEvery = repeatsEvery;
    }

    /**
     * Flag used by batch to mark event as expired. For Search.
     * 
     * @return
     *     The expired
     */
    @JsonProperty("expired")
    public Boolean getExpired() {
        return expired;
    }

    /**
     * Flag used by batch to mark event as expired. For Search.
     * 
     * @param expired
     *     The expired
     */
    @JsonProperty("expired")
    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(startTime).append(endTime).append(createdAt).append(updatedAt).append(repeatable).append(repeats).append(repeatTime).append(repeatsOn).append(dayOfMonth).append(repeatsEvery).append(expired).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TimeInfo) == false) {
            return false;
        }
        TimeInfo rhs = ((TimeInfo) other);
        return new EqualsBuilder().append(startTime, rhs.startTime).append(endTime, rhs.endTime).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).append(repeatable, rhs.repeatable).append(repeats, rhs.repeats).append(repeatTime, rhs.repeatTime).append(repeatsOn, rhs.repeatsOn).append(dayOfMonth, rhs.dayOfMonth).append(repeatsEvery, rhs.repeatsEvery).append(expired, rhs.expired).isEquals();
    }

    public enum Repeats {

        DAILY("DAILY"),
        WEEKLY("WEEKLY"),
        MONTHLY("MONTHLY");
        private final String value;
        private final static Map<String, TimeInfo.Repeats> CONSTANTS = new HashMap<String, TimeInfo.Repeats>();

        static {
            for (TimeInfo.Repeats c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Repeats(String value) {
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
        public static TimeInfo.Repeats fromValue(String value) {
            TimeInfo.Repeats constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
