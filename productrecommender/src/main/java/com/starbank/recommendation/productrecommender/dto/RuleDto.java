package com.starbank.recommendation.productrecommender.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.starbank.recommendation.productrecommender.model.Rule;
import com.starbank.recommendation.productrecommender.model.RuleCondition;
import lombok.Data;

import java.util.*;

@Data
public class RuleDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_id")
    private UUID productId;
    @JsonProperty("product_text")
    private String productText;
    @JsonProperty("rule")
    private List<RuleCondition> conditions;

    static public RuleDto mapToRuleConditionDto(Rule rule) {
        RuleDto dto = new RuleDto();
        dto.id = UUID.randomUUID();
        dto.productId = rule.getProductId();
        dto.productName = rule.getProductName();
        dto.productText = rule.getProductText();
        dto.conditions = new ArrayList<>(rule.getConditions().size());
        Collections.copy(dto.conditions, rule.getConditions());
        return dto;
    }
}
