package com.starbank.recommendation.ProductRecommender.repository;

import com.starbank.recommendation.ProductRecommender.model.Recommendation;
import com.starbank.recommendation.ProductRecommender.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;



    public ProductRepository(@Qualifier("h2Template") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isRecommendation(Recommendation recommendation, User user) {
        Boolean result = jdbcTemplate.queryForObject(
                recommendation.getSqlRules(),
                Boolean.class,
                user.getId());
        return result != null ? result : false;

    }

}
