package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_experience_info")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private Integer years;

    @Column(name = "company_name")
    private String companyName;

    private String position;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;
}