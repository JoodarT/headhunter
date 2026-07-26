package com.example.headhanter.dto;

import jakarta.validation.constraints.NotBlank;
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


    private List<EducationInfoDto> educations;

    private List<WorkExperienceInfoDto> workExperiences;

    private List<ContactsInfoDto> contacts;
}