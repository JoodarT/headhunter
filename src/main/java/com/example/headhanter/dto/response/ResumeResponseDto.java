package com.example.headhanter.dto.response;

import com.example.headhanter.dto.request.ContactInfoDto;
import com.example.headhanter.dto.request.EducationInfoDto;
import com.example.headhanter.dto.request.WorkExperienceInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDto {
    private Long id;
    private Long userId;
    private Long categoryId;
    private String categoryName;
    private String title;
    private String applicantName;
    private String skills;
    private Double expectedSalary;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private List<ContactInfoDto> contactInfos;
    private List<WorkExperienceInfoDto> experiences;
    private List<EducationInfoDto> educations;
}