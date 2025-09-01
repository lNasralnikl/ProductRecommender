package com.recom.bot.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.recom.bot.handler.UpdateHandler;

@Component
public class Bot extends TelegramLongPollingBot {
    private String token;

    private String username;

    private UpdateHandler updateHandler;

    public Bot(
        @Value("${telegram.bot.token}") String token,
        @Value("${telegram.bot.username}") String username,
        @Lazy UpdateHandler updateHandler
    ) {
        super(token);
        this.token = token;
        this.username = username;
        this.updateHandler = updateHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            updateHandler.handle(update.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
