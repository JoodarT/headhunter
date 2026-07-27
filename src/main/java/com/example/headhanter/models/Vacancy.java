package com.example.headhanter.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    private Long id;
    private String title;
    private String description;
    private Double salary;
    private Long categoryId;
    private Integer views;
    private Long employerId;
}