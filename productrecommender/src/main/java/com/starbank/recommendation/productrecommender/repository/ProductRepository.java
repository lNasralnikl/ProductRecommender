package com.starbank.recommendation.productrecommender.repository;

import com.starbank.recommendation.productrecommender.model.Recommendation;
import com.starbank.recommendation.productrecommender.model.RuleCondition;
import com.starbank.recommendation.productrecommender.model.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RuleRepository ruleRepository;

    public ProductRepository(JdbcTemplate jdbcTemplate, RuleRepository ruleRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.ruleRepository = ruleRepository;
    }

    @PostConstruct
    private void init() {
        log.info("H2 configured with {} datasource", jdbcTemplate.getDataSource());
    }

    public boolean isRecommendation(Recommendation recommendation, User user) {

        Boolean result = false;

        if (recommendation.getSqlRules() != null && !recommendation.getSqlRules().isEmpty()) {
            result = jdbcTemplate.queryForObject(
                    recommendation.getSqlRules(),
                    Boolean.class,
                    user.getId());
            return result != null ? result : false;
        }

        return false;

}

public boolean isRecommendation(List<RuleCondition> conditions, User user) {

    if (conditions == null || conditions.isEmpty()) return false;
    return conditions.stream().allMatch(condition -> isRuleCondition(condition, user));
}

private boolean isRuleCondition(RuleCondition condition, User user) {
    String sqlQuery = isRule(condition);
    Boolean result = jdbcTemplate.queryForObject(
            sqlQuery,
            Boolean.class,
            user.getId()
    );
    return result != null ? result : false;
}

private String isRule(RuleCondition condition) {

    String sqlQuery =
            "WITH UserTransactions AS ( " +
                    "SELECT t.amount, p.type, t.TYPE type_operation " +
                    "FROM TRANSACTIONS t " +
                    "JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? ) " +
                    "SELECT ";

    if (condition.isNegate()) sqlQuery += "NOT ";

    switch (condition.getQuery()) {
        case USER_OF: {
            sqlQuery +=
                    "EXISTS( " +
                            "SELECT 1 " +
                            "FROM UserTransactions ut " +
                            "WHERE ut.TYPE = '" + condition.getArguments().get(0) + "');";
            break;
        }
        case ACTIVE_USER_OF: {
            sqlQuery +=
                    "EXISTS( " +
                            "SELECT 5 " +
                            "FROM UserTransactions ut " +
                            "WHERE ut.TYPE = '" + condition.getArguments().get(0) + "');";
            break;
        }
        case TRANSACTION_SUM_COMPARE: {
            sqlQuery +=
                    "( " +
                            "SELECT SUM(ut.amount) " +
                            "FROM UserTransactions ut " +
                            "WHERE ut.TYPE = '" + condition.getArguments().get(0) + "' AND ut.type_operation = '" + condition.getArguments().get(1) + "' ) " +
                            condition.getArguments().get(2) + " " + condition.getArguments().get(3);
            break;
        }
        case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW: {
            sqlQuery +=
                    "( " +
                            "SELECT SUM(ut.amount) " +
                            "FROM UserTransactions ut " +
                            "WHERE ut.TYPE = '" + condition.getArguments().get(0) + "' AND ut.type_operation = 'DEPOSIT') " +
                            condition.getArguments().get(1) +
                            "( " +
                            "FROM UserTransactions ut " +
                            "WHERE ut.TYPE = '" + condition.getArguments().get(0) + "' AND ut.type_operation = 'WITHDRAW') ";
            break;
        }
        default: {
        }
    }

    return sqlQuery;
}

}