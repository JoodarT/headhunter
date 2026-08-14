package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "education_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;
    private String faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
}