package com.example.headhanter.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VacancyResponseDto {
    private Long id;
    private Long employerId;
    private String title;
    private String description;
    private BigDecimal salary;
    private Long categoryId;
    private Integer views;
    private Boolean isActive;
    private LocalDateTime updateTime;
}