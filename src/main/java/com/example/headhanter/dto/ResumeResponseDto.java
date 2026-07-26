package com.example.headhanter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDto {

    private Long id;
    private Long userId;
    private String applicantName;
    private String title;
    private String category;
    private String description;
    private String skills;
    private Double expectedSalary;

    private List<EducationInfoDto> educations;
    private List<WorkExperienceInfoDto> workExperiences;
    private List<ContactsInfoDto> contacts;
}