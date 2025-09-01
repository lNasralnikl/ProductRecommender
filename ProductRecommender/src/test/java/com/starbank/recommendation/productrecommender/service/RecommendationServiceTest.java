package com.starbank.recommendation.productrecommender.service;

import com.starbank.recommendation.productrecommender.dto.RecommendationDto;
import com.starbank.recommendation.productrecommender.model.Recommendation;
import com.starbank.recommendation.productrecommender.model.RuleStatistics;
import com.starbank.recommendation.productrecommender.model.User;
import com.starbank.recommendation.productrecommender.repository.ProductRepository;
import com.starbank.recommendation.productrecommender.repository.RuleCountStatisticsRepository;
import com.starbank.recommendation.productrecommender.repository.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private RuleCountStatisticsRepository ruleCountStatisticsRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    @Test
    void shouldReturnRecommendationsWhenUserMatches() {
        when(productRepository.isRecommendation(any(Recommendation.class), any(User.class)))
                .thenReturn(true);

        List<RecommendationDto> recommendations = recommendationService.getRecommendations(userId);

        assertThat(recommendations).isNotEmpty();
        assertThat(recommendations.get(0).getName()).isNotBlank();
    }

    @Test
    void shouldReturnEmptyListWhenNoMatch() {
        when(productRepository.isRecommendation(any(Recommendation.class), any(User.class)))
                .thenReturn(false);

        List<RecommendationDto> recommendations = recommendationService.getRecommendations(userId);

        assertThat(recommendations).isEmpty();
    }

    @Test
    void shouldDeleteRule() {
        UUID ruleId = UUID.randomUUID();

        recommendationService.deleteRule(ruleId);

        verify(ruleRepository, times(1)).deleteById(ruleId);
    }

    @Test
    void shouldIncreaseStatistics() {
        UUID ruleId = UUID.randomUUID();
        RuleStatistics stats = new RuleStatistics(ruleId);

        when(ruleCountStatisticsRepository.findById(ruleId)).thenReturn(Optional.of(stats));

        recommendationService.countIncrease(ruleId);

        assertThat(stats.getCount()).isEqualTo(1);
        verify(ruleCountStatisticsRepository).save(stats);
    }

    @Test
    void shouldReturnAllStats() {
        RuleStatistics stats1 = new RuleStatistics(UUID.randomUUID());
        RuleStatistics stats2 = new RuleStatistics(UUID.randomUUID());
        when(ruleCountStatisticsRepository.findAll()).thenReturn(List.of(stats1, stats2));

        List<RuleStatistics> result = recommendationService.getAllStats();

        assertThat(result).hasSize(2);
    }
}