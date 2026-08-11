package com.example.headhanter.models;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Resume {
    private Long id;
    private Long userId;
    private Long categoryId;
    private String title;
    private BigDecimal salary;
    private Boolean isActive;
    private LocalDateTime createdDate;

    private ContactsInfo contactInfo;
    private List<WorkExperienceInfo> experiences;
    private List<EducationInfo> educations;
}