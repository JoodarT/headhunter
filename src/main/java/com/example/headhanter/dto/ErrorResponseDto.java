package com.example.headhanter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String title;
    private List<String> reasons;


    public ErrorResponseDto(String title, String reason) {
        this.title = title;
        this.reasons = List.of(reason);
    }
}