package com.example.headhanter.service;

import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.models.Resume;
import com.example.headhanter.models.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {
    private final List<RespondedApplicant> responses = new ArrayList<>();
    private long currentId = 1;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private VacancyService vacancyService;

    public RespondedApplicant applyToVacancy(Long vacancyId, Long resumeId) {
        Resume resume = resumeService.getAllResumes().stream()
                .filter(r -> r.getId().equals(resumeId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Resume not found"));

        Vacancy vacancy = vacancyService.getAllVacancies().stream()
                .filter(v -> v.getId().equals(vacancyId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Vacancy not found"));

        RespondedApplicant response = new RespondedApplicant(
                currentId++,
                resume,
                vacancy,
                false
        );
        responses.add(response);
        return response;
    }

    public List<RespondedApplicant> getResponsesForVacancy(Long vacancyId) {
        return responses.stream()
                .filter(r -> r.getVacancy().getId().equals(vacancyId))
                .toList();
    }
}