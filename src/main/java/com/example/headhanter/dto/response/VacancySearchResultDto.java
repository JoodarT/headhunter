package com.example.headhanter.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class VacancySearchResultDto {
    private List<VacancyResponseDto> vacancies;
    private int currentPage;
    private int totalPages;
}
