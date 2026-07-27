package com.example.headhanter.service;

import com.example.headhanter.dao.ResumeDao;
import com.example.headhanter.dto.ResumeCreateDto;
import com.example.headhanter.dto.ResumeResponseDto;
import com.example.headhanter.models.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeDao resumeDao;

    public ResumeResponseDto createResume(ResumeCreateDto dto) {
        Resume resume = new Resume();
        resume.setUserId(dto.getUserId());
        resume.setApplicantName(dto.getApplicantName());
        resume.setTitle(dto.getTitle());
        resume.setCategory(dto.getCategory());
        resume.setSkills(dto.getSkills());
        resume.setExpectedSalary(dto.getExpectedSalary());

        Resume savedResume = resumeDao.save(resume);
        return mapToDto(savedResume);
    }

    public List<ResumeResponseDto> getAllResumes() {
        return resumeDao.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public ResumeResponseDto getResumeById(Long id) {
        Resume resume = resumeDao.findById(id);
        if (resume == null) {
            return null;
        }
        return mapToDto(resume);
    }

    public List<ResumeResponseDto> getResumesByUserId(Long userId) {
        return resumeDao.findByUserId(userId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public ResumeResponseDto updateResume(Long id, ResumeCreateDto dto) {
        Resume existingResume = resumeDao.findById(id);
        if (existingResume != null) {
            existingResume.setApplicantName(dto.getApplicantName());
            existingResume.setTitle(dto.getTitle());
            existingResume.setCategory(dto.getCategory());
            existingResume.setSkills(dto.getSkills());
            existingResume.setExpectedSalary(dto.getExpectedSalary());

            resumeDao.update(existingResume);
            return mapToDto(existingResume);
        }
        return null;
    }

    public List<ResumeResponseDto> searchResumes(String keyword) {
        List<Resume> resumes;
        if (keyword == null || keyword.trim().isEmpty()) {
            resumes = resumeDao.findAll();
        } else {
            resumes = resumeDao.searchResumes(keyword);
        }
        return resumes.stream().map(this::mapToDto).toList();
    }

    public List<ResumeResponseDto> getResumesByCategory(String category) {
        List<Resume> resumes;
        if (category == null || category.trim().isEmpty()) {
            resumes = resumeDao.findAll();
        } else {
            resumes = resumeDao.findByCategory(category);
        }
        return resumes.stream().map(this::mapToDto).toList();
    }

    public boolean deleteResume(Long id) {
        if (resumeDao.findById(id) != null) {
            resumeDao.deleteById(id);
            return true;
        }
        return false;
    }

    private ResumeResponseDto mapToDto(Resume resume) {
        ResumeResponseDto dto = new ResumeResponseDto();
        dto.setId(resume.getId());
        dto.setUserId(resume.getUserId());
        dto.setApplicantName(resume.getApplicantName());
        dto.setTitle(resume.getTitle());
        dto.setCategory(resume.getCategory());
        dto.setSkills(resume.getSkills());
        dto.setExpectedSalary(resume.getExpectedSalary());
        return dto;
    }
}