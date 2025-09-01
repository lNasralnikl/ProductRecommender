package com.starbank.recommendation.productrecommender.conf;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class H2Config {

    // === Primary: Postgres ===
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties pgDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource pgDataSource(@Qualifier("pgDataSourceProperties") DataSourceProperties props) {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl(props.getUrl());
        hc.setUsername(props.getUsername());
        hc.setPassword(props.getPassword());
        if (props.getDriverClassName() != null) {
            hc.setDriverClassName(props.getDriverClassName());
        }
        return new HikariDataSource(hc);
    }

    // === Secondary: H2 (file) ===
    @Bean
    @ConfigurationProperties("app.h2")
    public DataSourceProperties h2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "h2DataSource")
    public DataSource h2DataSource(@Qualifier("h2DataSourceProperties") DataSourceProperties props) {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl(props.getUrl());
        hc.setUsername(props.getUsername());
        hc.setPassword(props.getPassword());
        if (props.getDriverClassName() != null) {
            hc.setDriverClassName(props.getDriverClassName());
        }
        return new HikariDataSource(hc);
    }

    @Bean(name = "h2JdbcTemplate")
    public JdbcTemplate h2JdbcTemplate(@Qualifier("h2DataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}