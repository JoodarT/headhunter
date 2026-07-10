package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacancies")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double salary;

    @Column(name = "exp_from")
    private Integer expFrom;

    @Column(name = "exp_to")
    private Integer expTo;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}