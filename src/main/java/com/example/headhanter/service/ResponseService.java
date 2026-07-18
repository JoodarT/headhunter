package com.example.headhanter.service;

import com.example.headhanter.dao.RespondedApplicantDao;
import com.example.headhanter.dao.ResumeDao;
import com.example.headhanter.dao.VacancyDao;
import com.example.headhanter.models.RespondedApplicant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResponseService {

    private final RespondedApplicantDao respondedApplicantDao;
    private final ResumeDao resumeDao;
    private final VacancyDao vacancyDao;

    public void respondToVacancy(Long resumeId, Long vacancyId) {
        respondedApplicantDao.save(resumeId, vacancyId);
    }

    public List<RespondedApplicant> getAllResponses() {
        List<Map<String, Object>> rows = respondedApplicantDao.findAllRaw();
        List<RespondedApplicant> responses = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            responses.add(mapToModel(row));
        }
        return responses;
    }

    public RespondedApplicant getResponseById(Long id) {
        Map<String, Object> row = respondedApplicantDao.findByIdRaw(id);
        if (row == null) return null;
        return mapToModel(row);
    }

    public boolean updateConfirmation(Long id, boolean confirmation) {
        if (respondedApplicantDao.findByIdRaw(id) != null) {
            respondedApplicantDao.updateConfirmation(id, confirmation);
            return true;
        }
        return false;
    }

    public boolean deleteResponse(Long id) {
        if (respondedApplicantDao.findByIdRaw(id) != null) {
            respondedApplicantDao.deleteById(id);
            return true;
        }
        return false;
    }

    private RespondedApplicant mapToModel(Map<String, Object> row) {
        RespondedApplicant applicant = new RespondedApplicant();
        applicant.setId((Long) row.get("id"));
        applicant.setConfirmation((Boolean) row.get("confirmation"));

        Long resumeId = (Long) row.get("resume_id");
        Long vacancyId = (Long) row.get("vacancy_id");

        applicant.setResume(resumeDao.findById(resumeId));
        applicant.setVacancy(vacancyDao.findById(vacancyId));

        return applicant;
    }
}