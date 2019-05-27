
package com.yumu.eventsapiserv.pojos.system;

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
import org.joda.time.DateTime;


/**
 * a single log record
 * <p>
 * application log object
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "yumu_user_id",
    "type",
    "msg",
    "created_at"
})
public class AppLog {

    /**
     * id
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * api caller
     * 
     */
    @JsonProperty("yumu_user_id")
    @JsonPropertyDescription("")
    private String yumuUserId;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("type")
    private AppLog.Type type;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("msg")
    private String msg;
    /**
     * Time when the log was created
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    private DateTime createdAt;

    /**
     * id
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * id
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * api caller
     * 
     * @return
     *     The yumuUserId
     */
    @JsonProperty("yumu_user_id")
    public String getYumuUserId() {
        return yumuUserId;
    }

    /**
     * api caller
     * 
     * @param yumuUserId
     *     The yumu_user_id
     */
    @JsonProperty("yumu_user_id")
    public void setYumuUserId(String yumuUserId) {
        this.yumuUserId = yumuUserId;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public AppLog.Type getType() {
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
    public void setType(AppLog.Type type) {
        this.type = type;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The msg
     */
    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * (Required)
     * 
     * @param msg
     *     The msg
     */
    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Time when the log was created
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Time when the log was created
     * 
     * @param createdAt
     *     The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(yumuUserId).append(type).append(msg).append(createdAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AppLog) == false) {
            return false;
        }
        AppLog rhs = ((AppLog) other);
        return new EqualsBuilder().append(id, rhs.id).append(yumuUserId, rhs.yumuUserId).append(type, rhs.type).append(msg, rhs.msg).append(createdAt, rhs.createdAt).isEquals();
    }

    public enum Type {

        DEBUG("DEBUG"),
        ERROR("ERROR"),
        EXCEPTION("EXCEPTION"),
        INFO("INFO");
        private final String value;
        private final static Map<String, AppLog.Type> CONSTANTS = new HashMap<String, AppLog.Type>();

        static {
            for (AppLog.Type c: values()) {
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
        public static AppLog.Type fromValue(String value) {
            AppLog.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
