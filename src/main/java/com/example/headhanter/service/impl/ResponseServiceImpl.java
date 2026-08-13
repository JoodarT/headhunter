package com.example.headhanter.service.impl;

import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.models.Resume;
import com.example.headhanter.models.Vacancy;
import com.example.headhanter.repository.RespondedApplicantRepository;
import com.example.headhanter.service.ResponseService;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResponseServiceImpl implements ResponseService {

    private final RespondedApplicantRepository respondedApplicantRepository;
    private final VacancyService vacancyService;
    private final ResumeService resumeService;

    @Override
    public List<RespondedApplicant> getResponsesByVacancyId(Long vacancyId) {
        return respondedApplicantRepository.findByVacancyId(vacancyId);
    }

    @Override
    public List<Vacancy> getVacanciesRespondedByResume(Long resumeId) {
        List<RespondedApplicant> responses = respondedApplicantRepository.findByResumeId(resumeId);
        return responses.stream()
                .map(RespondedApplicant::getVacancy)
                .toList();
    }

    @Override
    @Transactional
    public RespondedApplicant respondToVacancy(Long resumeId, Long vacancyId) {
        Vacancy vacancy = vacancyService.findById(vacancyId);
        Resume resume = resumeService.findById(resumeId);

        RespondedApplicant response = RespondedApplicant.builder()
                .vacancy(vacancy)
                .resume(resume)
                .confirmation(false)
                .build();

        return respondedApplicantRepository.save(response);
    }

    @Override
    public List<RespondedApplicant> getAllResponses() {
        return respondedApplicantRepository.findAll();
    }

    @Override
    @Transactional
    public void updateConfirmationStatus(Long id, boolean status) {
        RespondedApplicant response = respondedApplicantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отклик с id " + id + " не найден"));

        response.setConfirmation(status);
        respondedApplicantRepository.save(response);
    }
}