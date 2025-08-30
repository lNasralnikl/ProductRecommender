package com.recom.bot.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {
    
    boolean support(String command);

    void handle(Message message);
}
