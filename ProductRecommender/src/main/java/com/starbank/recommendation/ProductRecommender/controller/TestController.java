package com.starbank.recommendation.ProductRecommender.controller;

import java.util.List;
import java.util.UUID;

import com.starbank.recommendation.ProductRecommender.model.Recommendation;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final JdbcTemplate h2Template;

    private final CaffeineCacheManager cacheManager;

    public TestController(JdbcTemplate h2Template, CaffeineCacheManager cacheManager) {
        this.h2Template = h2Template;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/test-cache")
    public List<Recommendation> getCachedRecommendation(@RequestParam UUID userId) {
        Cache cache = cacheManager.getCache("recommendations");

        return cache.get(userId, List.class);
    }

}
