package com.starbank.recommendation.ProductRecommender.repository;

import com.starbank.recommendation.ProductRecommender.model.RuleStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleCountStatisticsRepository extends JpaRepository<RuleStatistics, UUID> {
}
