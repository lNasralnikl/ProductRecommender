package com.starbank.recommendation.ProductRecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recommendation_rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_text", nullable = false)
    private String productText;

    @JsonIgnore
    @Column(name = "rule_query", nullable = false)
    @Enumerated(EnumType.STRING)
    private QueryType query;

    @JsonIgnore
    @Column(name = "arguments", columnDefinition = "jsonb")
    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    private boolean negate;

    public enum QueryType {
        USER_OF("USER_OF"),
        ACTIVE_USER_OF("ACTIVE_USER_OF"),
        TRANSACTION_SUM_COMPARE("TRANSACTION_SUM_COMPARE"),
        TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");

        private String query;

        QueryType(String query) {
            this.query = query;
        }

        @JsonValue
        public String getQuery() {
            return query;
        }
    }

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

    //Тут добавить конвертер по рекомендации наставника


}
