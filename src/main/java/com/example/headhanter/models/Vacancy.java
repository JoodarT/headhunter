package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    private Long id;
    private Long employerId;
    private String title;
    private String description;
    private Double salary;
    private String category;
}