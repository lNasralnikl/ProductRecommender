package com.starbank.recommendation.productrecommender.repository;

import com.starbank.recommendation.productrecommender.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID> {
}
