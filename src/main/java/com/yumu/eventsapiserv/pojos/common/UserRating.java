
package com.yumu.eventsapiserv.pojos.common;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;


/**
 * User Rating
 * <p>
 * User rating for the app
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "email",
    "phone",
    "owner",
    "rating",
    "content",
    "created_at"
})
public class UserRating {

    /**
     * encrypted id of this resource
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * name of the author.
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("")
    private String name;
    /**
     * email of the author.
     * 
     */
    @JsonProperty("email")
    @JsonPropertyDescription("")
    private String email;
    /**
     * phone of the author.
     * 
     */
    @JsonProperty("phone")
    @JsonPropertyDescription("")
    private String phone;
    /**
     * creator of the rating.
     * 
     */
    @JsonProperty("owner")
    @JsonPropertyDescription("")
    private String owner;
    /**
     *  1 to 5 rating
     * (Required)
     * 
     */
    @NotNull
    @JsonProperty("rating")
    @JsonPropertyDescription("")
    @DecimalMin("1")
    @DecimalMax("5")
    private Integer rating;
    /**
     * content
     * 
     */
    @JsonProperty("content")
    @JsonPropertyDescription("")
    @Size(max = 2500)
    private String content;
    /**
     * Time when the post was created
     * 
     */
    @JsonProperty("created_at")
    @JsonPropertyDescription("")
    private DateTime createdAt;

    /**
     * encrypted id of this resource
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
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * name of the author.
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * name of the author.
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * email of the author.
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * email of the author.
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * phone of the author.
     * 
     * @return
     *     The phone
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * phone of the author.
     * 
     * @param phone
     *     The phone
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * creator of the rating.
     * 
     * @return
     *     The owner
     */
    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    /**
     * creator of the rating.
     * 
     * @param owner
     *     The owner
     */
    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *  1 to 5 rating
     * (Required)
     * 
     * @return
     *     The rating
     */
    @JsonProperty("rating")
    public Integer getRating() {
        return rating;
    }

    /**
     *  1 to 5 rating
     * (Required)
     * 
     * @param rating
     *     The rating
     */
    @JsonProperty("rating")
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * content
     * 
     * @return
     *     The content
     */
    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    /**
     * content
     * 
     * @param content
     *     The content
     */
    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Time when the post was created
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public DateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Time when the post was created
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
        return new HashCodeBuilder().append(id).append(name).append(email).append(phone).append(owner).append(rating).append(content).append(createdAt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UserRating) == false) {
            return false;
        }
        UserRating rhs = ((UserRating) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(email, rhs.email).append(phone, rhs.phone).append(owner, rhs.owner).append(rating, rhs.rating).append(content, rhs.content).append(createdAt, rhs.createdAt).isEquals();
    }

}
