package com.starbank.recommendation.ProductRecommender.conf;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class H2Config {

    @Value("${h2.url}")
    private String url;

    @Bean
    DataSource h2DataSource() {
        HikariConfig conf = new HikariConfig();

        conf.setJdbcUrl(url);
        conf.setDriverClassName("org.h2.Driver");
        conf.setReadOnly(true);
        conf.setMaximumPoolSize(10);

        return new HikariDataSource(conf);
    }

    @Bean
    JdbcTemplate h2Template(DataSource h2DataSource) {
        return new JdbcTemplate(h2DataSource);
    }

    @Bean(name = "defaultDataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

}