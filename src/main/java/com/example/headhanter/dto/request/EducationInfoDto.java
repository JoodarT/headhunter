package com.example.headhanter.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EducationInfoDto {

    @NotBlank(message = "Учебное заведение обязательно")
    private String institution;

    @NotBlank(message = "Специальность обязательна")
    private String faculty;

    @NotNull(message = "Укажите год окончания")
    private Integer graduationYear;
}