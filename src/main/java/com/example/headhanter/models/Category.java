package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Vacancy> vacancies = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<Resume> resumes = new ArrayList<>();
}