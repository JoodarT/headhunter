package com.example.headhanter.models;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Resume {
    private Long id;
    private Long userId;
    private String applicantName;
    private String title;
    private String category;
    private String skills;
    private Double expectedSalary;
    private LocalDateTime updateTime;

    private ContactsInfo contactInfo;
    private List<WorkExperienceInfo> experiences;
    private List<EducationInfo> educations;
}