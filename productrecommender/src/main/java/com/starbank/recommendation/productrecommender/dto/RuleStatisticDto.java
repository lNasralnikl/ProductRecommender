package com.starbank.recommendation.productrecommender.dto;

import java.util.List;
import java.util.UUID;

public class RuleStatisticDto {

    private List<RuleStatistic> statistics;

    public RuleStatisticDto(List<RuleStatistic> statistics) {
        this.statistics = statistics;
    }

    public List<RuleStatistic> getStatistics() {
        return statistics;
    }

    public static class RuleStatistic{
        private final UUID ruleId;
        private final Long count;

        public RuleStatistic(UUID ruleId, Long count) {
            this.ruleId = ruleId;
            this.count = count;
        }

        public UUID getRuleId() {
            return ruleId;
        }

        public Long getCount() {
            return count;
        }
    }

}