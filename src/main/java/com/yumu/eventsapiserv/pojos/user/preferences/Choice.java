
package com.yumu.eventsapiserv.pojos.user.preferences;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * User's choice
 * <p>
 * User's choices
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "category",
    "selections"
})
public class Choice {

    /**
     * selections should come from config file
     * 
     */
    @JsonProperty("category")
    @JsonPropertyDescription("")
    private String category;
    @JsonProperty("selections")
    @Valid
    private List<Selection> selections = new ArrayList<Selection>();

    /**
     * selections should come from config file
     * 
     * @return
     *     The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * selections should come from config file
     * 
     * @param category
     *     The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The selections
     */
    @JsonProperty("selections")
    public List<Selection> getSelections() {
        return selections;
    }

    /**
     * 
     * @param selections
     *     The selections
     */
    @JsonProperty("selections")
    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(category).append(selections).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Choice) == false) {
            return false;
        }
        Choice rhs = ((Choice) other);
        return new EqualsBuilder().append(category, rhs.category).append(selections, rhs.selections).isEquals();
    }

}
