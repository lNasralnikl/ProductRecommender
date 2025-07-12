package com.starbank.recommendation.ProductRecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Recommendation {

    @JsonProperty("product_id")
    private String name;
    private UUID id;
    @JsonProperty("product_text")
    private String text;

    @JsonProperty("rule")
    private List<Rule> rules;

    @JsonIgnore
    private String sqlRules = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSqlRules() {
        return sqlRules;
    }

    public void setSqlRules(String sqlRules) {
        this.sqlRules = sqlRules;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
