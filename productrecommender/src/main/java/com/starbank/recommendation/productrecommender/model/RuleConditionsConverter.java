package com.starbank.recommendation.productrecommender.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class RuleConditionsConverter implements AttributeConverter<List<RuleCondition>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public String convertToDatabaseColumn(List<RuleCondition> conditions) {
        try {

            return mapper.writeValueAsString(conditions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<RuleCondition> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, new TypeReference<List<RuleCondition>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
