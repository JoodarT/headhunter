package com.example.headhanter.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WorkExperienceInfoDto {

    @NotBlank(message = "{validation.experience.company.notBlank}")
    private String companyName;

    @NotBlank(message = "{validation.experience.position.notBlank}")
    private String position;

    @NotBlank(message = "{validation.experience.period.notBlank}")
    private String period;

    private String responsibilities;
}