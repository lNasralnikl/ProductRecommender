package com.recom.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.recom.bot.service.MessageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {

    private final MessageService messageService;

    @Override
    public boolean support(String command) {
        return command.startsWith("/start");
    }

    @Override
    public void handle(Message message) {
        String chatId = message.getChatId().toString();

        messageService.sendMessage(chatId, "Hello!");
    }
    
}
