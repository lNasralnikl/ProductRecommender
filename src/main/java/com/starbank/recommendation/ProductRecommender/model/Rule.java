package com.starbank.recommendation.ProductRecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.UUID;

public class Rule {

    public enum QueryType {
        USER_OF("USER_OF"),
        ACTIVE_USER_OF("ACTIVE_USER_OF"),
        TRANSACTION_SUM_COMPARE("TRANSACTION_SUM_COMPARE"),
        TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("RANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");

        private String query;

        QueryType(String query) {
            this.query = query;
        }

        @JsonValue
        public String getQuery() {
            return query;
        }
    }

    @JsonIgnore
    private UUID id;
    private QueryType query;
    private List<String> arguments;
    private boolean negate;

    public QueryType getQuery() {
        return query;
    }

    public void setQuery(QueryType query) {
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
