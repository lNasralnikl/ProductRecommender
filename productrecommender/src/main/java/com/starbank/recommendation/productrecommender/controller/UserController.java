package com.starbank.recommendation.productrecommender.controller;

import com.starbank.recommendation.productrecommender.model.User;
import com.starbank.recommendation.productrecommender.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable UUID id) {
        return userService.findById(id);
    }
}
