package com.example.headhanter.service;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.Vacancy;

import java.util.List;

public interface VacancyService {
    List<Vacancy> findAllActive();

    VacancyResponseDto create(VacancyCreateDto dto);
    List<VacancyResponseDto> getAll();
    VacancyResponseDto getById(Long id);
    List<VacancyResponseDto> getRespondedVacanciesByUser(Long userId);
    VacancyResponseDto update(Long id, VacancyCreateDto dto, Long currentUserId);
    void delete(Long id, Long currentUserId);

    Vacancy findById(Long id);
    Vacancy create(Vacancy vacancy, Long employerId, Long categoryId);
    Vacancy update(Long id, Vacancy updatedVacancy, Long categoryId);
    void incrementViews(Long id);
}