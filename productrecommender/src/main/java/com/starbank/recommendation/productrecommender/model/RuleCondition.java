package com.starbank.recommendation.productrecommender.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RuleCondition {

    @JsonProperty("query")
    private Rule.QueryType query;

    @JsonProperty("arguments")
    private List<String> arguments;

    @JsonProperty("negate")
    private boolean negate;

    public RuleCondition(Rule.QueryType query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public RuleCondition() {

    }

    public static RuleCondition of(Rule.QueryType query, List<String> arguments, boolean negate) {
        return new RuleCondition(query, arguments, negate);
    }

    public Rule.QueryType getQuery() {
        return query;
    }

    public void setQuery(Rule.QueryType query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }
}
