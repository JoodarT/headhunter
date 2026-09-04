package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import  java.util.ArrayList;


@Entity
@Table(name = "responded_applicants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespondedApplicant {

    @OneToMany(mappedBy = "respondedApplicant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default

    private List<Message> messages = new ArrayList<>();
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