package com.example.headhanter.service.impl;

import com.example.headhanter.dto.request.ContactsInfoDto;
import com.example.headhanter.dto.request.EducationInfoDto;
import com.example.headhanter.dto.request.ResumeCreateDto;
import com.example.headhanter.dto.request.WorkExperienceInfoDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.models.*;
import com.example.headhanter.repository.CategoryRepository;
import com.example.headhanter.repository.ResumeRepository;
import com.example.headhanter.repository.UserRepository;
import com.example.headhanter.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ResumeResponseDto createResume(ResumeCreateDto dto) {
        Resume resume = new Resume();
        mapDtoToEntity(dto, resume);
        resume.setCreatedDate(LocalDateTime.now());
        resume.setIsActive(true);

        Resume savedResume = resumeRepository.save(resume);
        return mapToDto(savedResume);
    }

    @Override
    public List<ResumeResponseDto> getAllResumes() {
        return resumeRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public ResumeResponseDto getResumeById(Long id) {
        Resume resume = findById(id);
        return mapToDto(resume);
    }

    @Override
    public List<ResumeResponseDto> getResumesByUserId(Long userId) {
        return resumeRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @Transactional
    public ResumeResponseDto updateResume(Long id, ResumeCreateDto dto, Long currentUserId) {
        Resume existingResume = findById(id);

        if (existingResume.getUser() == null || !existingResume.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("У вас нет прав на редактирование этого резюме");
        }

        mapDtoToEntity(dto, existingResume);

        Resume updatedResume = resumeRepository.save(existingResume);
        return mapToDto(updatedResume);
    }

    @Override
    public List<ResumeResponseDto> searchResumes(String keyword) {
        List<Resume> resumes;
        if (keyword == null || keyword.trim().isEmpty()) {
            resumes = resumeRepository.findAll();
        } else {
            resumes = resumeRepository.findAll();
        }
        return resumes.stream().map(this::mapToDto).toList();
    }

    @Override
    public List<ResumeResponseDto> getResumesByCategory(Long categoryId) {
        List<Resume> resumes;
        if (categoryId == null) {
            resumes = resumeRepository.findAll();
        } else {
            resumes = resumeRepository.findByCategoryId(categoryId);
        }
        return resumes.stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public boolean deleteResume(Long id, Long currentUserId) {
        Resume existingResume = findById(id);

        if (existingResume.getUser() == null || !existingResume.getUser().getId().equals(currentUserId)) {
            throw new AccessDeniedException("У вас нет прав на удаление этого резюме");
        }

        resumeRepository.deleteById(id);
        return true;
    }

    @Override
    public Resume findById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Резюме с ID " + id + " не найдено"));
    }

    @Override
    public List<Resume> findByUserId(Long userId) {
        return resumeRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Resume create(Resume resume, Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Категория не найдена"));

        resume.setUser(user);
        resume.setCategory(category);
        resume.setCreatedDate(LocalDateTime.now());
        return resumeRepository.save(resume);
    }

    private void mapDtoToEntity(ResumeCreateDto dto, Resume resume) {
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
            resume.setUser(user);
        }

        resume.setTitle(dto.getTitle());

        if (dto.getCategory() != null && !dto.getCategory().isBlank()) {
            try {
                Long categoryId = Long.parseLong(dto.getCategory());
                categoryRepository.findById(categoryId).ifPresent(resume::setCategory);
            } catch (NumberFormatException ignored) {
            }
        }

        if (dto.getExpectedSalary() != null) {
            resume.setSalary(BigDecimal.valueOf(dto.getExpectedSalary()));
        }
    }

    private ResumeResponseDto mapToDto(Resume resume) {
        ResumeResponseDto dto = new ResumeResponseDto();
        dto.setId(resume.getId());

        if (resume.getUser() != null) {
            dto.setUserId(resume.getUser().getId());
        }

        if (resume.getCategory() != null) {
            dto.setCategoryId(resume.getCategory().getId());
        }

        dto.setTitle(resume.getTitle());

        if (resume.getSalary() != null) {
            dto.setExpectedSalary(resume.getSalary().doubleValue());
        }

        dto.setCreatedDate(resume.getCreatedDate());
        dto.setExperiences(Collections.emptyList());
        dto.setEducations(Collections.emptyList());

        return dto;
    }
}