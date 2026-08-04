package com.example.headhanter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EducationInfoDto {

    @NotBlank(message = "Учебное заведение обязательно")
    private String institution;

    @NotBlank(message = "Специальность обязательна")
    private String faculty;

    private Integer graduationYear;
}