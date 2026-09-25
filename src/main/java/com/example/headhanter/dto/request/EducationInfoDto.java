package com.example.headhanter.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EducationInfoDto {

    @NotBlank(message = "{validation.education.institution.notBlank}")
    private String institution;

    @NotBlank(message = "{validation.education.faculty.notBlank}")
    private String faculty;

    @NotNull(message = "{validation.education.enrollmentYear.notNull}")
    private Integer enrollmentYear;

    @NotNull(message = "{validation.education.graduationYear.notNull}")
    private Integer graduationYear;



}