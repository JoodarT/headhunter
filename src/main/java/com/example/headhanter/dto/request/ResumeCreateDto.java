package com.example.headhanter.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class ResumeCreateDto {

    private Long userId;

    private String applicantName;

    @NotBlank(message = "{validation.resume.title.notBlank}")
    private String title;

    @NotNull(message = "{validation.resume.category.notNull}")
    private Long categoryId;

    private String skills;

    @NotNull(message = "{validation.resume.salary.notNull}")
    @Positive(message = "{validation.resume.salary.positive}")
    private Double expectedSalary;

    @NotNull(message = "{validation.resume.contacts.notNull}")
    @Valid
    private List<ContactInfoDto> contactInfos;

    @NotEmpty(message = "{validation.resume.experiences.notEmpty}")
    @Valid
    private List<WorkExperienceInfoDto> experiences;

    @Valid
    private List<EducationInfoDto> educations;
}