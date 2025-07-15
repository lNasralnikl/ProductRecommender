package com.starbank.recommendation.ProductRecommender.repository;

import com.starbank.recommendation.ProductRecommender.model.Recommendation;
import com.starbank.recommendation.ProductRecommender.model.Rule;
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

        Boolean result = false;

        if (recommendation.getSqlRules() == null && recommendation.getSqlRules().equals("")) {
            result = jdbcTemplate.queryForObject(
                    recommendation.getSqlRules(),
                    Boolean.class,
                    user.getId());
            return result != null ? result : false;
        } else return isRecommendation(recommendation.getRules(), user);

    }

    public boolean isRecommendation(List<Rule> rules, User user) {

        if (rules == null || rules.isEmpty()) return false;
        return rules.stream().allMatch(rule -> isRule(rule, user));
    }

    private boolean isRule(Rule rule, User user) {

        String sqlQuery =
                "WITH UserTransactions AS ( " +
                        "SELECT t.amount, p.type, t.TYPE type_operation " +
                        "FROM TRANSACTIONS t " +
                        "JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? ) " +
                        "SELECT ";

        if (rule.isNegate()) sqlQuery += "NOT ";

        switch (rule.getQuery()) {
            case USER_OF: {
                sqlQuery +=
                        "EXISTS( " +
                                "SELECT 1 " +
                                "FROM UserTransactions ut " +
                                "WHERE ut.TYPE = '" + rule.getArguments().get(0) + "');";
                break;
            }
            case ACTIVE_USER_OF: {
                sqlQuery +=
                        "EXISTS( " +
                                "SELECT 5 " +
                                "FROM UserTransactions ut " +
                                "WHERE ut.TYPE = '" + rule.getArguments().get(0) + "');";
                break;
            }
            case TRANSACTION_SUM_COMPARE: {
                sqlQuery +=
                        "( " +
                                "SELECT SUM(ut.amount) " +
                                "FROM UserTransactions ut " +
                                "WHERE ut.TYPE = '" + rule.getArguments().get(0) + "' AND ut.type_operation = '" + rule.getArguments().get(1) + "' ) " +
                                rule.getArguments().get(2) + " " + rule.getArguments().get(3);
                break;
            }
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW: {
                sqlQuery +=
                        "( " +
                                "SELECT SUM(ut.amount) " +
                                "FROM UserTransactions ut " +
                                "WHERE ut.TYPE = '" + rule.getArguments().get(0) + "' AND ut.type_operation = 'DEPOSIT') " +
                                rule.getArguments().get(1) +
                                "( " +
                                "FROM UserTransactions ut " +
                                "WHERE ut.TYPE = '" + rule.getArguments().get(0) + "' AND ut.type_operation = 'WITHDRAW') ";
                break;
            }
            default: {
            }
        }

        return jdbcTemplate.queryForObject(sqlQuery, Boolean.class, user.getId());
    }

}
