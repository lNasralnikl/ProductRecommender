package com.starbank.recommendation.ProductRecommender.rules;

public class RecommendationRules {

    public static final String INVEST_500_RULE = "WITH UserTransactions AS (\n" +
            "    SELECT t.amount, p.type, t.TYPE type_operation\n" +
            "    FROM TRANSACTIONS t\n" +
            "    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID\n" +
            "    WHERE t.USER_ID = ?\n" +
            ")\n" +
            "SELECT \n" +
            "    EXISTS(\n" +
            "        SELECT 1 \n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'DEBIT'\n" +
            "    ) \n" +
            "    AND NOT EXISTS(\n" +
            "        SELECT 1 \n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'INVEST'\n" +
            "    )\n" +
            "    AND (\n" +
            "        SELECT SUM(ut.amount)\n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'SAVING' AND ut.type_operation = 'DEPOSIT'\n" +
            "    ) > 1000 ";

    public static final String TOP_SAVING_RULE = "WITH UserTransactions AS (\n" +
            "    SELECT t.amount, p.type, t.TYPE type_operation\n" +
            "    FROM TRANSACTIONS t\n" +
            "    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID\n" +
            "    WHERE t.USER_ID = ?\n" +
            ")\n" +
            "SELECT \n" +
            "    EXISTS(\n" +
            "        SELECT 1 \n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'DEBIT'\n" +
            "    ) \n" +
            "    AND \n" +
            "    ( \n" +
            "\t    (\n" +
            "\t        SELECT SUM(ut.amount) \n" +
            "\t        FROM UserTransactions ut\n" +
            "\t        WHERE ut.TYPE = 'DEBIT' AND ut.type_operation = 'DEPOSIT'\n" +
            "\t    ) >= 50000\n" +
            "    OR\n" +
            "\t    (\n" +
            "\t        SELECT SUM(ut.amount) \n" +
            "\t        FROM UserTransactions ut\n" +
            "\t        WHERE ut.TYPE = 'SAVING' AND ut.type_operation = 'DEPOSIT'\n" +
            "\t    ) >= 50000\n" +
            "    )\n" +
            "    AND (\n" +
            "        SELECT SUM(ut.amount)\n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'DEBIT' AND ut.type_operation = 'DEPOSIT'\n" +
            "    ) > (\n" +
            "        SELECT SUM(ut.amount)\n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'DEBIT' AND ut.type_operation = 'WITHDRAW'\n" +
            "    )";

    public static final String SIMPLE_CREDIT_RULE = "WITH UserTransactions AS (\n" +
            "    SELECT t.amount, p.type, t.TYPE type_operation\n" +
            "    FROM TRANSACTIONS t\n" +
            "    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID\n" +
            "    WHERE t.USER_ID = ?\n" +
            ")\n" +
            "SELECT \n" +
            "    NOT EXISTS(\n" +
            "        SELECT 1 \n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'CREDIT'\n" +
            "    ) \n" +
            "    AND (\n" +
            "        SELECT SUM(ut.amount)\n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'DEBIT' AND ut.type_operation = 'DEPOSIT'\n" +
            "    ) > (\n" +
            "        SELECT SUM(ut.amount)\n" +
            "        FROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'DEBIT' AND ut.type_operation = 'WITHDRAW'\n" +
            "    )\n" +
            "    AND (\n" +
            "    \tSELECT SUM(ut.amount)\n" +
            "    \tFROM UserTransactions ut\n" +
            "        WHERE ut.TYPE = 'DEBIT' AND ut.type_operation = 'WITHDRAW'\n" +
            "    ) > 100000";
}
