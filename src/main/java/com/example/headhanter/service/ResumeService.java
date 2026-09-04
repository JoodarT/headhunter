package com.example.headhanter.service;

import com.example.headhanter.dto.request.ResumeCreateDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.models.Resume;

import java.util.List;

public interface ResumeService {
    ResumeResponseDto createResume(ResumeCreateDto dto);
    List<ResumeResponseDto> getAllResumes();
    ResumeResponseDto getResumeById(Long id);
    List<ResumeResponseDto> getResumesByUserId(Long userId);
    ResumeResponseDto updateResume(Long id, ResumeCreateDto dto, Long currentUserId);
    List<ResumeResponseDto> searchResumes(String keyword);
    boolean deleteResume(Long id, Long currentUserId);

    Resume findById(Long id);
}