package com.starbank.recommendation.ProductRecommender.controller;

import com.starbank.recommendation.ProductRecommender.model.Recommendation;
import com.starbank.recommendation.ProductRecommender.model.Rule;
import com.starbank.recommendation.ProductRecommender.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Recommendation>> getRecommendations(@PathVariable UUID userId){
        List<Recommendation> recommendations = recommendationService.getRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }

    @PostMapping("/rule")
    public ResponseEntity<Rule> addRule(@RequestBody Rule rule){
        Rule savedRule = recommendationService.addRule(rule);
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


}
