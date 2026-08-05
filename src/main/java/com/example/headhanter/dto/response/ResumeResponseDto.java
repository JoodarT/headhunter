package com.example.headhanter.dto.response;

import com.example.headhanter.dto.ContactsInfoDto;
import com.example.headhanter.dto.EducationInfoDto;
import com.example.headhanter.dto.WorkExperienceInfoDto;
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