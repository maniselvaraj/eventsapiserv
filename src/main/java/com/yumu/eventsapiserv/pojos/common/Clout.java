
package com.yumu.eventsapiserv.pojos.common;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Clout or Ranking or Popularity
 * <p>
 * element's clout
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "rank"
})
public class Clout {

    /**
     * a single number rank for now
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("rank")
    private Double rank;

    /**
     * a single number rank for now
     * (Required)
     * 
     * @return
     *     The rank
     */
    @JsonProperty("rank")
    public Double getRank() {
        return rank;
    }

    /**
     * a single number rank for now
     * (Required)
     * 
     * @param rank
     *     The rank
     */
    @JsonProperty("rank")
    public void setRank(Double rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(rank).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Clout) == false) {
            return false;
        }
        Clout rhs = ((Clout) other);
        return new EqualsBuilder().append(rank, rhs.rank).isEquals();
    }

}
