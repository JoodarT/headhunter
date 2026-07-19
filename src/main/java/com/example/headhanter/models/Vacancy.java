package com.example.headhanter.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vacancy {
    private Long id;
    private Long employerId;
    private String title;
    private String description;
    private Double salary;
    private String category;
    private Integer views;
}