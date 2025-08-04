package com.starbank.recommendation.ProductRecommender.controller;

import com.starbank.recommendation.ProductRecommender.dto.RecommendationDto;
import com.starbank.recommendation.ProductRecommender.dto.RuleDto;
import com.starbank.recommendation.ProductRecommender.dto.RuleStatisticDto;
import com.starbank.recommendation.ProductRecommender.model.Recommendation;
import com.starbank.recommendation.ProductRecommender.model.Rule;
import com.starbank.recommendation.ProductRecommender.model.RuleStatistics;
import com.starbank.recommendation.ProductRecommender.service.RecommendationService;
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
    public ResponseEntity<List<RecommendationDto>> getRecommendations(@PathVariable UUID userId) {
        List<RecommendationDto> recommendations = recommendationService.getRecommendations(userId);
        return ResponseEntity.ok(recommendations);
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


}
