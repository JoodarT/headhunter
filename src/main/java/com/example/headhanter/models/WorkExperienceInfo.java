package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfo {
    private Long id;
    private Integer years;
    private String companyName;
    private String position;
    private String responsibilities;
}