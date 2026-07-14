package com.example.headhanter.service;

import com.example.headhanter.models.RespondedApplicant;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {
    private final List<RespondedApplicant> responses = new ArrayList<>();
    private long currentId = 1;

    public RespondedApplicant applyToVacancy(Long vacancyId, Long resumeId) {
        RespondedApplicant response = new RespondedApplicant(
                currentId++,
                vacancyId,
                resumeId,
                LocalDateTime.now(),
                "SENT"
        );
        responses.add(response);
        return response;
    }

    public List<RespondedApplicant> getResponsesForVacancy(Long vacancyId) {
        return responses.stream().filter(r -> r.getVacancyId().equals(vacancyId)).toList();
    }
}