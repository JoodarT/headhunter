package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "responded_applicants")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespondedApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy;

    private Boolean confirmation = false;
}