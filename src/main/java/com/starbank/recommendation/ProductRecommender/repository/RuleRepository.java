package com.starbank.recommendation.ProductRecommender.repository;

import com.starbank.recommendation.ProductRecommender.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RuleRepository extends JpaRepository<Rule, UUID> {
}
