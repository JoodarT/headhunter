package com.example.headhanter.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VacancyCreateDto {

    @NotBlank(message = "Название вакансии не может быть пустым")
    @Size(min = 3, max = 150, message = "Название вакансии должно быть от 3 до 150 символов")
    private String title;

    @NotBlank(message = "Описание вакансии обязательно")
    @Size(min = 10, max = 3000, message = "Описание должно содержать минимум 10 символов")
    private String description;

    @NotNull(message = "Укажите уровень зарплаты")
    @Min(value = 0, message = "Зарплата не может быть отрицательной")
    private Double salary;

    @NotNull(message = "Категория должна быть указана")
    private Long categoryId;

    @NotNull(message = "Работодатель должен быть указан")
    private Long employerId;
}