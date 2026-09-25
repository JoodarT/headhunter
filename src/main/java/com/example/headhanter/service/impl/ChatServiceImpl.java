package com.example.headhanter.service.impl;

import com.example.headhanter.dto.response.ChatMessageResponseDto;
import com.example.headhanter.dto.response.ChatViewDto;
import com.example.headhanter.models.Message;
import com.example.headhanter.models.Resume;
import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.models.User;
import com.example.headhanter.models.Vacancy;
import com.example.headhanter.repository.MessageRepository;
import com.example.headhanter.repository.RespondedApplicantRepository;
import com.example.headhanter.service.ChatService;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {

    private final MessageRepository messageRepository;
    private final RespondedApplicantRepository respondedApplicantRepository;
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    @Override
    public ChatViewDto getChatView(Long responseId, Long currentUserId) {
        RespondedApplicant response = findResponse(responseId);
        assertParticipant(response, currentUserId);

        List<ChatMessageResponseDto> messages = messageRepository
                .findByRespondedApplicantIdOrderByTimestampAsc(responseId).stream()
                .map(this::mapToDto)
                .toList();

        return mapToView(response, currentUserId, messages);
    }

    @Override
    @Transactional
    public ChatMessageResponseDto sendMessage(Long responseId, Long senderId, String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Сообщение не может быть пустым");
        }

        RespondedApplicant response = findResponse(responseId);
        assertParticipant(response, senderId);

        User sender = userService.getUserById(senderId);

        Message message = Message.builder()
                .respondedApplicant(response)
                .sender(sender)
                .content(content.trim())
                .timestamp(LocalDateTime.now())
                .build();

        return mapToDto(messageRepository.save(message));
    }

    @Override
    @Transactional
    public RespondedApplicant startChat(Long resumeId, Long vacancyId, Long employerId) {
        Vacancy vacancy = vacancyService.findById(vacancyId);
        if (vacancy.getEmployer() == null || !vacancy.getEmployer().getId().equals(employerId)) {
            throw new AccessDeniedException("Вы можете начать диалог только от имени своей вакансии");
        }

        Resume resume = resumeService.findById(resumeId);

        return respondedApplicantRepository.findByVacancyIdAndResumeId(vacancyId, resumeId)
                .orElseGet(() -> respondedApplicantRepository.save(
                        RespondedApplicant.builder()
                                .vacancy(vacancy)
                                .resume(resume)
                                .confirmation(false)
                                .build()
                ));
    }

    private RespondedApplicant findResponse(Long responseId) {
        return respondedApplicantRepository.findById(responseId)
                .orElseThrow(() -> new NoSuchElementException("Чат с id " + responseId + " не найден"));
    }

    private void assertParticipant(RespondedApplicant response, Long currentUserId) {
        boolean isApplicant = response.getResume() != null && response.getResume().getUser() != null
                && response.getResume().getUser().getId().equals(currentUserId);
        boolean isEmployer = response.getVacancy() != null && response.getVacancy().getEmployer() != null
                && response.getVacancy().getEmployer().getId().equals(currentUserId);

        if (!isApplicant && !isEmployer) {
            throw new AccessDeniedException("У вас нет доступа к этому чату");
        }
    }

    private ChatViewDto mapToView(RespondedApplicant response, Long currentUserId, List<ChatMessageResponseDto> messages) {
        boolean isEmployerViewer = response.getVacancy().getEmployer().getId().equals(currentUserId);
        User counterpart = isEmployerViewer ? response.getResume().getUser() : response.getVacancy().getEmployer();

        ChatViewDto dto = new ChatViewDto();
        dto.setResponseId(response.getId());
        dto.setEmployerViewer(isEmployerViewer);
        dto.setVacancyTitle(response.getVacancy().getTitle());
        dto.setResumeTitle(response.getResume().getTitle());
        dto.setMessages(messages);

        if (counterpart != null) {
            dto.setCounterpartId(counterpart.getId());
            dto.setCounterpartName(counterpart.getName());
            dto.setCounterpartEmail(counterpart.getEmail());
            dto.setCounterpartPhone(counterpart.getPhone());
            dto.setCounterpartAvatarUrl(counterpart.getAvatarUrl());
        }

        return dto;
    }

    private ChatMessageResponseDto mapToDto(Message message) {
        ChatMessageResponseDto dto = new ChatMessageResponseDto();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());

        if (message.getSender() != null) {
            dto.setSenderId(message.getSender().getId());
            dto.setSenderName(message.getSender().getName());
        }

        return dto;
    }
}
