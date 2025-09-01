package com.recom.bot.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.telegram.telegrambots.meta.bots.AbsSender;

@TestConfiguration
public class TestBotConfig {

    @Bean
    @Primary
    public AbsSender mockTelegramBot() {
        return Mockito.mock(AbsSender.class, Mockito.RETURNS_DEEP_STUBS);
    }
}