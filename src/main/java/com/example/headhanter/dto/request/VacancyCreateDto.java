package com.example.headhanter.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VacancyCreateDto {

    @NotBlank(message = "{validation.vacancy.company.notBlank}")
    @Size(min = 4, max = 100, message = "{validation.vacancy.company.size}")
    private String company;

    @NotBlank(message = "{validation.vacancy.title.notBlank}")
    @Size(min = 3, max = 150, message = "{validation.vacancy.title.size}")
    private String title;

    @NotBlank(message = "{validation.vacancy.description.notBlank}")
    @Size(min = 10, max = 3000, message = "{validation.vacancy.description.size}")
    private String description;

    @NotNull(message = "{validation.vacancy.salary.notNull}")
    @Min(value = 0, message = "{validation.vacancy.salary.min}")
    private BigDecimal salary;

    @NotNull(message = "{validation.vacancy.experienceFrom.notNull}")
    @Min(value = 0, message = "{validation.vacancy.experienceFrom.min}")
    @Max(value = 50, message = "{validation.vacancy.experienceFrom.max}")
    private Integer experienceFrom;

    @NotNull(message = "{validation.vacancy.experienceTo.notNull}")
    @Min(value = 0, message = "{validation.vacancy.experienceTo.min}")
    @Max(value = 50, message = "{validation.vacancy.experienceTo.max}")
    private Integer experienceTo;

    @NotNull(message = "{validation.vacancy.category.notNull}")
    private Long categoryId;

    private Long employerId;

    private Boolean isActive;

    @AssertTrue(message = "{validation.vacancy.experience.range}")
    public boolean isExperienceRangeValid() {
        if (experienceFrom == null || experienceTo == null) {
            return true;
        }
        return experienceTo >= experienceFrom;
    }
}