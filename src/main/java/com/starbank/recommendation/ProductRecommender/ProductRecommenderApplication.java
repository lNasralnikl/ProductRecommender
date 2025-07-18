package com.starbank.recommendation.ProductRecommender;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class ProductRecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductRecommenderApplication.class, args);
	}

}
