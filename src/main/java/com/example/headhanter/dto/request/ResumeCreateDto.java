package com.example.headhanter.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data // <-- Важно! Генерирует геттеры и сеттеры
public class ResumeCreateDto {

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    private String applicantName; // <-- Если называется иначе, переименуйте здесь или в сервисе

    @NotBlank(message = "Название резюме обязательно")
    private String title;

    @NotBlank(message = "Укажите категорию")
    private String category; // <-- Проверьте наличие

    private String skills; // <-- Именно этого поля не хватает для dto.getSkills()

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