package com.starbank.recommendation.productrecommender.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "rule_statistics")
public class RuleStatistics {

    @Id
    @Column(name = "rule_id")
    private UUID ruleId;

    @Column(name = "count")
    private Long count = 0L;

    public RuleStatistics(UUID ruleId, Long count) {
        this.ruleId = ruleId;
        this.count = count != null ? count:0L;
    }

    public RuleStatistics(UUID ruleId) {
    }

    public RuleStatistics() {
    }

    //Повышение счетчика
    public void incrementCount(){
        this.count++;
    }

    public UUID getRuleId() {
        return ruleId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
