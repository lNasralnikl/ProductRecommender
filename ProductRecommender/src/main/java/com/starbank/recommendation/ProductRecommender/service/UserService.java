package com.starbank.recommendation.ProductRecommender.service;

import com.starbank.recommendation.ProductRecommender.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JdbcTemplate h2Template;

    public User findById(UUID id) {
        return h2Template.queryForObject(
                "SELECT * FROM USERS WHERE ID = ?",
                new BeanPropertyRowMapper<>(User.class),
                id);
    }
}
