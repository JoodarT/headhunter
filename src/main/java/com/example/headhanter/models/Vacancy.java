package com.example.headhanter.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    private Long id;
    private String title;
    private String description;
    private BigDecimal salary;
    private Long categoryId;
    private Integer views;
    private Long employerId;
    private Boolean isActive;
    private LocalDateTime updateTime;
}