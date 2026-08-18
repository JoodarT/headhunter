package com.example.headhanter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyResponseDto {
    private String name;
    private long vacancyCount;
}
