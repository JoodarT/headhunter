package com.example.headhanter.service;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.VacancyResponseDto;

import java.util.List;

public interface VacancyService {
    VacancyResponseDto create(VacancyCreateDto dto);
    List<VacancyResponseDto> getAll();
    VacancyResponseDto getById(Long id);
    List<VacancyResponseDto> getRespondedVacanciesByUser(Long userId);
    VacancyResponseDto update(Long id, VacancyCreateDto dto);
    void delete(Long id);
}