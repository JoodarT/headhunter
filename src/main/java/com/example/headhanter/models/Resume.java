package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Column(nullable = false)
    private String name;

    private Double salary;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}