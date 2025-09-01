package com.recom.bot.handler;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.recom.bot.service.MessageService;

@ExtendWith(MockitoExtension.class)
public class HandlerTest {
    
    @InjectMocks
    private UpdateHandler updateHandler;

    @Mock
    private CommandHandler startCommandHandler;

    @Mock
    private CommandHandler recommendationCommandHandler;

    @Mock
    private MessageService messageService;

    @Test
    void handleStartCommand() {
        when(startCommandHandler.support("/start")).thenReturn(true);

        updateHandler = new UpdateHandler(
            List.of(startCommandHandler), 
            messageService
        );

        Message message = mock(Message.class);
        when(message.getText()).thenReturn("/start");
        updateHandler.handle(message);

        verify(startCommandHandler).handle(message);
    }

    @Test
    void handleRecommednationCommand() {
        when(startCommandHandler.support("/recommendation")).thenReturn(true);

        updateHandler = new UpdateHandler(
            List.of(startCommandHandler), 
            messageService
        );

        Message message = mock(Message.class);
        when(message.getText()).thenReturn("/recommendation");
        updateHandler.handle(message);

        verify(startCommandHandler).handle(message);
    }

    @Test
    void handleUnknownCommand() {
        when(startCommandHandler.support(anyString())).thenReturn(false);

        updateHandler = new UpdateHandler(
            List.of(startCommandHandler, recommendationCommandHandler), 
            messageService
        );

        Message message = mock(Message.class);
        when(message.getText()).thenReturn("/unknown");
        updateHandler.handle(message);

        verify(startCommandHandler, never()).handle(message);
        verify(recommendationCommandHandler, never()).handle(message);
        verify(messageService).sendMessage(anyString(), eq("Unknown command"));
    }
}
