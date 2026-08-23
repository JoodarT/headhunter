package com.example.headhanter.service;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.CompanyResponseDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.Vacancy;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VacancyService {
    VacancyResponseDto create(VacancyCreateDto dto);
    List<VacancyResponseDto> getAll();
    Page<VacancyResponseDto> getAllPaged(int page, int size, boolean sortByResponses, boolean ascending);
    VacancyResponseDto getById(Long id);
    List<VacancyResponseDto> getRespondedVacanciesByUser(Long userId);
    List<VacancyResponseDto> searchVacancy(String keywords);
    List<VacancyResponseDto> getVacanciesByEmployer(Long employerId);

    Page<VacancyResponseDto> getVacanciesByCompany(String company, int page, int size);
    List<CompanyResponseDto> getAllCompanies();
    VacancyResponseDto update(Long id, VacancyCreateDto dto, Long currentUserId);
    void delete(Long id, Long currentUserId);

    Vacancy findById(Long id);
    void incrementViews(Long id);
}