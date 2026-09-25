package com.example.headhanter.service;

import com.example.headhanter.dto.response.ChatMessageResponseDto;
import com.example.headhanter.dto.response.ChatViewDto;
import com.example.headhanter.models.RespondedApplicant;

public interface ChatService {
    ChatViewDto getChatView(Long responseId, Long currentUserId);
    ChatMessageResponseDto sendMessage(Long responseId, Long senderId, String content);
    RespondedApplicant startChat(Long resumeId, Long vacancyId, Long employerId);
}
