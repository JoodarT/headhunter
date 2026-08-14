package com.example.headhanter.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WorkExperienceInfoDto {

    @NotBlank(message = "Название компании обязательно")
    private String companyName;

    @NotBlank(message = "Должность обязательна")
    private String position;

    @NotBlank(message = "Укажите период работы")
    private String period;

    private String responsibilities;
}