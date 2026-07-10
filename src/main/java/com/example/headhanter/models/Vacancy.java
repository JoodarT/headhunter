package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vacancy {
    private Long id;
    private String name;
    private String description;
    private User author;
}