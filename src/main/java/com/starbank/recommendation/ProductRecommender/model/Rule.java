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
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty("product_name")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @JsonProperty("product_id")
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @JsonProperty("product_text")
    @Column(name = "product_text", nullable = false)
    private String productText;

    @Convert(converter = RuleConditionsConverter.class)
    @JsonProperty("rule")
    @Column(name =  "rule_conditions", columnDefinition = "jsonb")
    private List<RuleCondition> conditions;

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

    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<RuleCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<RuleCondition> conditions) {
        this.conditions = conditions;
    }
}
