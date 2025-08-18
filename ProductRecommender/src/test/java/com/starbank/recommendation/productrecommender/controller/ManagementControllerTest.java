package com.starbank.recommendation.productrecommender.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ManagementController.class)
class ManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaffeineCacheManager cacheManager;

    @MockBean
    private BuildProperties buildProperties;

    private Cache mockCache;

    @BeforeEach
    void setUp() {
        mockCache = Mockito.mock(Cache.class);

        when(buildProperties.getName()).thenReturn("product-recommender");
        when(buildProperties.getVersion()).thenReturn("1.0.0");

        when(cacheManager.getCacheNames()).thenReturn(List.of("recommendations"));
        when(cacheManager.getCache("recommendations")).thenReturn(mockCache);
    }

    @Test
    void clearCaches_ShouldClearAllCaches() throws Exception {
        mockMvc.perform(post("/management/clear-caches"))
                .andExpect(status().isOk());

        verify(cacheManager, times(1)).getCacheNames();
        verify(mockCache, times(1)).clear();
    }

    @Test
    void getInfo_ShouldReturnBuildInfo() throws Exception {
        mockMvc.perform(get("/management/info")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("product-recommender"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }
}