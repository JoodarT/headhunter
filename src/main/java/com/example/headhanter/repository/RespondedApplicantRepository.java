package com.example.headhanter.repository;

import com.example.headhanter.models.RespondedApplicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespondedApplicantRepository extends JpaRepository<RespondedApplicant, Long> {
    List<RespondedApplicant> findByVacancyId(Long vacancyId);
    List<RespondedApplicant> findByResumeId(Long resumeId);
    List<RespondedApplicant> findByVacancyEmployerId(Long employerId);
    List<RespondedApplicant> findByResumeUserId(Long userId);
    boolean existsByVacancyIdAndResumeId(Long vacancyId, Long resumeId);
}