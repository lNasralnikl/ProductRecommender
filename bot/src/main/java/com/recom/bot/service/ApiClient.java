package com.recom.bot.service;

import java.util.List;
import java.util.UUID;

import com.recom.bot.conf.FeignConfig;
import com.recom.bot.util.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.recom.bot.util.RecommendationDto;

@FeignClient(name = "recom-api", configuration = FeignConfig.class)
public interface ApiClient {
    @GetMapping("/recommendation/{userId}")
    public ResponseEntity<List<RecommendationDto>> getRecommendations(@PathVariable UUID userId);

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable UUID id);
}
