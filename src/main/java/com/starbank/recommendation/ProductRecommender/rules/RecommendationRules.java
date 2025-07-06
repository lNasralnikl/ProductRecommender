package com.starbank.recommendation.ProductRecommender.rules;

public class RecommendationRules {

    public static final String INVEST_500_RULE = "SELECT count(*) FILTER(WHERE products.type='DEBIT') > 0 AND count(*) FILTER(WHERE products.type='INVEST') = 0 AND sum(amount) FILTER(WHERE products.TYPE='SAVING') > 1000\n" +
            "    FROM transactions\n" +
            "    INNER JOIN products ON transactions.product_id = products.id\n" +
            "    WHERE user_id = ?";

    public static final String TOP_SAVING_RULE = "SELECT count(*) FILTER(WHERE products.type='DEBIT') > 0 AND \n" +
            "     ( sum(amount) FILTER(WHERE products.type='DEBIT' AND transactions.type='DEPOSIT') >= 50000 OR \n" +
            "     sum(amount) FILTER(WHERE products.type='SAVING' AND transactions.TYPE='DEPOSIT') >= 50000 ) AND\n" +
            "     sum(amount) FILTER(WHERE products.TYPE='DEBIT' AND transactions.TYPE='DEPOSIT') > sum(amount) FILTER(WHERE products.TYPE='DEBIT' AND transactions.TYPE='WITHDRAW')\n" +
            "    FROM transactions\n" +
            "    INNER JOIN products ON transactions.product_id = products.id\n" +
            "    WHERE user_id = ?";

    public static final String SIMPLE_CREDIT_RULE = "SELECT count(*) FILTER(WHERE products.type='CREDIT') = 0 AND\n" +
            "  sum(amount) FILTER(WHERE products.TYPE='DEBIT' AND transactions.TYPE='DEPOSIT') > sum(amount) FILTER(WHERE products.TYPE='DEBIT' AND transactions.TYPE='WITHDRAW') AND\n" +
            "  sum(amount) FILTER(WHERE products.type='DEBIT' AND transactions.TYPE = 'WITHDRAW') > 100000\n" +
            "    FROM transactions\n" +
            "    INNER JOIN products ON transactions.product_id = products.id\n" +
            "    WHERE user_id = ?";
}
