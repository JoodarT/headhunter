package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicant {
    private Long id;
    private Resume resume;
    private Vacancy vacancy;
    private Boolean confirmation;
}