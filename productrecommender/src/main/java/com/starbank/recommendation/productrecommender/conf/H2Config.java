package com.starbank.recommendation.productrecommender.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class H2Config {

    @Bean(name = "defaultDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "h2DataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource h2DataSource() {
        return new HikariDataSource();
    }

    @Bean
    public JdbcTemplate h2Template(@Qualifier("h2DataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}