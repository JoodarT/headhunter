package com.example.headhanter.dto;

import lombok.Data;

@Data
public class VacancyCreateDto {
    private Long employerId;
    private String title;
    private String description;
    private Double salary;
    private String category;
}