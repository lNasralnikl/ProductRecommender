package com.starbank.recommendation.productrecommender.controller;

import com.starbank.recommendation.productrecommender.dto.RecommendationDto;
import com.starbank.recommendation.productrecommender.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    @Test
    void shouldReturnRecommendationsForValidUser() throws Exception {
        UUID userId = UUID.fromString("cd515076-5d8a-44be-930e-8d4fcb79f42d");

        RecommendationDto dto1 = new RecommendationDto();
        dto1.setId(UUID.randomUUID());
        dto1.setName("Product1");
        dto1.setText("Описание 1");

        RecommendationDto dto2 = new RecommendationDto();
        dto2.setId(UUID.randomUUID());
        dto2.setName("Product2");
        dto2.setText("Описание 2");

        when(recommendationService.getRecommendations(userId))
                .thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/recommendation/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnEmptyListForUnknownUser() throws Exception {
        UUID userId = UUID.randomUUID();

        when(recommendationService.getRecommendations(userId))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/recommendation/{userId}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}