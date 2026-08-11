package com.example.headhanter.dto.response;

import com.example.headhanter.dto.request.ContactsInfoDto;
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
    private String applicantName;
    private String title;
    private String category;
    private String skills;
    private Double expectedSalary;
    private LocalDateTime updateTime;

    private ContactsInfoDto contactInfo;
    private List<WorkExperienceInfoDto> experiences;
    private List<EducationInfoDto> educations;
}