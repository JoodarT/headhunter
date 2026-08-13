package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "responded_applicants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespondedApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", nullable = false)
    private Vacancy vacancy;

    @Column(name = "confirmation")
    @Builder.Default
    private Boolean confirmation = false;
}