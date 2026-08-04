package com.example.headhanter.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ResumeCreateDto {

    private Long userId;

    private String applicantName;

    @NotBlank(message = "Заголовок резюме не может быть пустым")
    private String title;

    private String category;

    private String description;

    private String skills;

    private Double expectedSalary;

    @Valid
    private List<EducationInfoDto> educations;

    @Valid
    @NotEmpty(message = "Добавьте хотя бы одно место работы")
    private List<WorkExperienceInfoDto> workExperiences;

    @Valid
    @NotNull(message = "Контактная информация обязательна")
    private List<ContactsInfoDto> contacts;
}