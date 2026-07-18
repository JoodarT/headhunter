package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicant {
    private Long id;
    private Resume resume;
    private Vacancy vacancy;
    private Boolean confirmation;
}