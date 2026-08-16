package com.example.headhanter.service;

import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.models.Vacancy;

import java.util.List;

public interface ResponseService {
    List<RespondedApplicant> getResponsesByVacancyId(Long vacancyId);
    List<Vacancy> getVacanciesRespondedByResume(Long resumeId);
    RespondedApplicant respondToVacancy(Long resumeId, Long vacancyId, Long currentUserId);
    boolean hasResponded(Long vacancyId, Long resumeId);
    List<RespondedApplicant> getAllResponses();
    void updateConfirmationStatus(Long id, boolean status);
}