package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
    private Long id;
    private String applicantName;
    private String title;
    private String category;
    private String skills;

    private Double expectedSalary;
}