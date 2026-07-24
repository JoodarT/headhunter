package com.example.headhanter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VacancyCreateDto {

    @NotBlank(message = "Название вакансии не может быть пустым")
    private String title;

    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Укажите зарплату")
    @Positive(message = "Зарплата должна быть больше 0")
    private Double salary;

    @NotNull(message = "Укажите ID категории")
    private Long categoryId;

    @NotNull(message = "Укажите ID работодателя")
    private Long employerId;
}