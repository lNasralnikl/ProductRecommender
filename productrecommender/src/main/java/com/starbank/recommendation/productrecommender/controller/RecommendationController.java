package com.starbank.recommendation.productrecommender.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starbank.recommendation.productrecommender.dto.RecommendationDto;
import com.starbank.recommendation.productrecommender.dto.RuleDto;
import com.starbank.recommendation.productrecommender.dto.RuleStatisticDto;
import com.starbank.recommendation.productrecommender.model.Rule;
import com.starbank.recommendation.productrecommender.model.RuleStatistics;
import com.starbank.recommendation.productrecommender.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendation/{userId}")
    public ResponseEntity<RecommendationResponse> getRecommendations(@PathVariable UUID userId) {
        List<RecommendationDto> recommendations = recommendationService.getRecommendations(userId);
        return ResponseEntity.ok(new RecommendationResponse(userId, recommendations));
    }

    @PostMapping("/rule")
    public ResponseEntity<RuleDto> addRule(@RequestBody Rule rule){
        RuleDto savedRule = recommendationService.addRule(rule);
        return ResponseEntity.ok(savedRule);
    }

    @GetMapping("/rule")
    public ResponseEntity<List<Rule>> getAllRules(){
        List<Rule> rules = recommendationService.getAllRules();
        return ResponseEntity.ok(rules);
    }

    @DeleteMapping("/rule/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID id){
        recommendationService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

    //Статистика срабатываний рекомендаций
    @GetMapping("/rule/stats")
    public RuleStatisticDto getRuleStatistics(){
        List<RuleStatistics> statistics = recommendationService.getAllStats();

        List<RuleStatisticDto.RuleStatistic> response = statistics.stream()
                .map(stats -> new RuleStatisticDto.RuleStatistic(
                        stats.getRuleId(),
                        stats.getCount()
                ))
                .collect(Collectors.toList());
        return new RuleStatisticDto(response);
    }

    public static record RecommendationResponse(@JsonProperty("user_id") UUID id, List<RecommendationDto> recommendations) {
    }



}
