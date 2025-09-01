package com.starbank.recommendation.productrecommender.controller;

import com.starbank.recommendation.productrecommender.model.User;
import com.starbank.recommendation.productrecommender.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testFindById() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        user.setUserName("User");
        user.setFirstName("FirstUser");
        user.setLastName("LastUser");

        when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(id.toString()))
                .andExpect(jsonPath("$.userName").value("User"))
                .andExpect(jsonPath("$.firstName").value("FirstUser"))
                .andExpect(jsonPath("$.lastName").value("LastUser"));
    }
}