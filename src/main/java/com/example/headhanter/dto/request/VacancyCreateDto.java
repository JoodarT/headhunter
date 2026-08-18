package com.example.headhanter.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VacancyCreateDto {

    @NotBlank (message =  "Название компании обязательно")
    @Size(min = 4, max = 100, message = "Название компании должно быть от 4 до 100 символов")
    private String company;

    @NotBlank(message = "Название вакансии не может быть пустым")
    @Size(min = 3, max = 150, message = "Название вакансии должно быть от 3 до 150 символов")
    private String title;

    @NotBlank(message = "Описание вакансии обязательно")
    @Size(min = 10, max = 3000, message = "Описание должно содержать минимум 10 символов")
    private String description;

    @NotNull(message = "Укажите уровень зарплаты")
    @Min(value = 0, message = "Зарплата не может быть отрицательной")
    private BigDecimal salary;

    @NotNull(message = "Категория должна быть указана")
    private Long categoryId;

    private Long employerId;


}