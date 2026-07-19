package com.example.headhanter.dto;

import lombok.Data;

@Data
public class VacancyResponseDto {
    private Long id;
    private Long employerId;
    private String title;
    private String description;
    private Double salary;
    private String category;
    private Integer views;
}