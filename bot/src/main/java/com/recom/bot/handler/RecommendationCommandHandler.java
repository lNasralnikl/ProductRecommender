package com.recom.bot.handler;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.recom.bot.service.ApiClient;
import com.recom.bot.service.MessageService;
import com.recom.bot.util.RecommendationDto;
import com.recom.bot.util.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecommendationCommandHandler implements CommandHandler {
    
    private final MessageService messageService;

    private final ApiClient client;

    @Override
    public boolean support(String command) {
        return command.startsWith("/recommend");
    }

    @Override
    public void handle(Message message) {
        String chatId = message.getChatId().toString();
        String[] parts = message.getText().split(" ");

        if (parts.length == 2) {
            UUID uuid;
            try {
                uuid = UUID.fromString(parts[1]);
            } catch (IllegalArgumentException e) {
                messageService.sendMessage(chatId, "Некорректный uuid");
                return;
            }

            List<RecommendationDto> recommendation = client.getRecommendations(uuid).getBody();
            User user = client.findUserById(uuid);

            if (recommendation != null && recommendation.size() != 1) {
                messageService.sendMessage(chatId, "Пользователь не найден");
                return;
            }

            String name = user.getFirstName() + " " + user.getLastName();
            String recomText = recommendation.get(0).getName() + recommendation.get(0).getText();

            String answer = """
                Здраствуйте %s! 
                У нас есть рекомендация для Вас: %s
                """.formatted(name, recomText);

                messageService.sendMessage(chatId, answer);
        } else {
            messageService.sendMessage(chatId, "Не предоставлен uuid пользователя");
        }
    }
    
}
