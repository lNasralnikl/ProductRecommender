package com.starbank.recommendation.productrecommender.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class User {

    @JsonProperty("user_id")
    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private List<Recommendation> recommendations;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
