package com.example.headhanter.dto;

import lombok.Data;

@Data
public class EducationInfoDto {
    private String institution;
    private String program;
    private Integer startYear;
    private Integer endYear;
}