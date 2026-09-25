package com.example.headhanter.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageResponseDto {
    private Long id;
    private Long senderId;
    private String senderName;
    private String content;
    private LocalDateTime timestamp;
}
