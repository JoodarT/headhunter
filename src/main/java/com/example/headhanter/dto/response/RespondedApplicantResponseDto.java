package com.example.headhanter.dto.response;

import lombok.Data;

@Data
public class RespondedApplicantResponseDto {
    private Long id;
    private Long vacancyId;
    private String vacancyTitle;
    private String company;
    private Long resumeId;
    private String resumeTitle;
    private String applicantName;
    private Boolean confirmation;
}
