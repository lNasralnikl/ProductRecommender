package com.starbank.recommendation.productrecommender.dto;

import com.starbank.recommendation.productrecommender.model.Recommendation;
import lombok.Data;

import java.util.UUID;

@Data
public class RecommendationDto {
    private String name;
    private UUID id;
    private String text;

    static public RecommendationDto mapToRecommendationDto(Recommendation recommendation) {
        RecommendationDto dto = new RecommendationDto();
        dto.name = recommendation.getName();
        dto.id = recommendation.getId();
        dto.text = recommendation.getText();
        return dto;
    }
}
