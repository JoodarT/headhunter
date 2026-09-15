package com.example.headhanter.controller.ws;

import com.example.headhanter.dto.request.ChatMessageRequest;
import com.example.headhanter.dto.response.ChatMessageResponseDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.ChatService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{responseId}/send")
    public void sendMessage(
            @DestinationVariable Long responseId,
            @Payload ChatMessageRequest request,
            Principal principal
    ) {
        if (principal == null) {
            return;
        }

        try {
            User sender = userService.getUserByEmail(principal.getName());
            ChatMessageResponseDto message = chatService.sendMessage(responseId, sender.getId(), request.getContent());
            messagingTemplate.convertAndSend("/topic/chat/" + responseId, message);
        } catch (RuntimeException e) {
            log.warn("Не удалось отправить сообщение в чат {}: {}", responseId, e.getMessage());
        }
    }
}
