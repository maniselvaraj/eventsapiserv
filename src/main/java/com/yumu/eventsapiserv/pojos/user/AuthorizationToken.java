
package com.yumu.eventsapiserv.pojos.user;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * yumu auth token
 * <p>
 * yumu auth token
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "token",
    "user_id",
    "time_created",
    "expiration_date"
})
public class AuthorizationToken {

    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("token")
    @JsonPropertyDescription("")
    private String token;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("user_id")
    @JsonPropertyDescription("")
    private String userId;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("time_created")
    @JsonPropertyDescription("")
    private Date timeCreated;
    /**
     * 
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("expiration_date")
    @JsonPropertyDescription("")
    private Date expirationDate;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The token
     */
    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    /**
     * 
     * (Required)
     * 
     * @param token
     *     The token
     */
    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The userId
     */
    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * (Required)
     * 
     * @param userId
     *     The user_id
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The timeCreated
     */
    @JsonProperty("time_created")
    public Date getTimeCreated() {
        return timeCreated;
    }

    /**
     * 
     * (Required)
     * 
     * @param timeCreated
     *     The time_created
     */
    @JsonProperty("time_created")
    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The expirationDate
     */
    @JsonProperty("expiration_date")
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param expirationDate
     *     The expiration_date
     */
    @JsonProperty("expiration_date")
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(token).append(userId).append(timeCreated).append(expirationDate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AuthorizationToken) == false) {
            return false;
        }
        AuthorizationToken rhs = ((AuthorizationToken) other);
        return new EqualsBuilder().append(token, rhs.token).append(userId, rhs.userId).append(timeCreated, rhs.timeCreated).append(expirationDate, rhs.expirationDate).isEquals();
    }

}
