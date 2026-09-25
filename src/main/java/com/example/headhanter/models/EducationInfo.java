package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "enrollment_year")
    private Integer enrollmentYear;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;


}