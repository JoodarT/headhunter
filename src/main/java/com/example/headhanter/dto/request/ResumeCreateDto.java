package com.example.headhanter.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class ResumeCreateDto {

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    private String applicantName;

    @NotBlank(message = "Название резюме обязательно")
    private String title;

    @NotBlank(message = "Укажите категорию")
    private String category;

    private String skills;

    @NotNull(message = "Укажите желаемую зарплату")
    @Positive(message = "Зарплата должна быть больше 0")
    private Double expectedSalary;

    @NotNull(message = "Контактные данные обязательны")
    @Valid
    private ContactsInfoDto contactInfo;

    @NotEmpty(message = "Добавьте хотя бы одно место работы")
    @Valid
    private List<WorkExperienceInfoDto> experiences;

    @Valid
    private List<EducationInfoDto> educations;
}