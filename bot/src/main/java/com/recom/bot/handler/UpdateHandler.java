package com.recom.bot.handler;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.recom.bot.service.MessageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final List<CommandHandler> handlers;

    private final MessageService messageService;

    public void handle(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        
        Optional<CommandHandler> handler = handlers.stream()
            .filter(h -> h.support(text))
            .findFirst();

        if (handler.isPresent()) {
            handler.get().handle(message);
        } else {
            messageService.sendMessage(chatId, "Unknown command");
        }
    }
}
