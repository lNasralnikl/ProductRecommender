package com.starbank.recommendation.ProductRecommender.controller;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final JdbcTemplate h2Template;

    public TestController(JdbcTemplate h2Template) {
        this.h2Template = h2Template;
    }

    @GetMapping("/query")
    public List<Map<String, Object>> testQuery() {
        return h2Template.queryForList("SELECT * FROM PRODUCTS");
    }
}
