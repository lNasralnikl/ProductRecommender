package com.recom.bot.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.recom.bot.conf.Bot;

@ExtendWith(MockitoExtension.class)
public class MessageserviceTest {
    
    @InjectMocks
    private MessageService messageService;
    
    @Mock
    private Bot bot;

    @Test
    void sendMessage_success() throws TelegramApiException {
        String chatId = "chatId";
        String text = "text";
        SendMessage expectedMessage = new SendMessage(chatId, text);
        
        messageService.sendMessage(chatId, text);

        verify(bot).execute(expectedMessage);
    }

    @Test
    void throwException_whenSendMessage() throws TelegramApiException {
        doThrow(TelegramApiException.class).when(bot).execute(any(SendMessage.class));

        assertThrows(
            RuntimeException.class, 
            () -> messageService.sendMessage("chatId", "text")
        );
    }
}
