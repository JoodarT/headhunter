package com.example.headhanter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ChatViewDto {
    private Long responseId;
    private boolean employerViewer;

    private String vacancyTitle;
    private String resumeTitle;

    private Long counterpartId;
    private String counterpartName;
    private String counterpartEmail;
    private String counterpartPhone;
    private String counterpartAvatarUrl;

    private List<ChatMessageResponseDto> messages;
}
