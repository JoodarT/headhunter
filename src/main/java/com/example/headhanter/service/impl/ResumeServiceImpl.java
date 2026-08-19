package com.example.headhanter.service.impl;

import com.example.headhanter.dto.request.ContactInfoDto;
import com.example.headhanter.dto.request.EducationInfoDto;
import com.example.headhanter.dto.request.ResumeCreateDto;
import com.example.headhanter.dto.request.WorkExperienceInfoDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.models.*;
import com.example.headhanter.repository.ResumeRepository;
import com.example.headhanter.service.CategoryService;
import com.example.headhanter.service.ContactTypeService;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ContactTypeService contactTypeService;

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
        List<Resume> resumes = (keyword == null || keyword.trim().isEmpty())
                ? resumeRepository.findAll()
                : resumeRepository.searchByKeyword(keyword.trim());
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

    private void mapDtoToEntity(ResumeCreateDto dto, Resume resume) {
        if (dto.getUserId() != null) {
            resume.setUser(userService.getUserById(dto.getUserId()));
        }

        resume.setTitle(dto.getTitle());
        resume.setApplicantName(dto.getApplicantName());
        resume.setSkills(dto.getSkills());

        if (dto.getCategoryId() != null) {
            resume.setCategory(categoryService.getById(dto.getCategoryId()));
        }

        if (dto.getExpectedSalary() != null) {
            resume.setSalary(BigDecimal.valueOf(dto.getExpectedSalary()));
        }

        syncContactInfos(resume, dto.getContactInfos());
        syncExperiences(resume, dto.getExperiences());
        syncEducations(resume, dto.getEducations());
    }

    private void syncContactInfos(Resume resume, List<ContactInfoDto> contactInfos) {
        resume.getContactInfos().clear();
        if (contactInfos == null) {
            return;
        }
        for (ContactInfoDto d : contactInfos) {
            if (d.getContactTypeId() == null || d.getValue() == null || d.getValue().isBlank()) {
                continue;
            }
            resume.getContactInfos().add(ContactInfo.builder()
                    .contactType(contactTypeService.getById(d.getContactTypeId()))
                    .value(d.getValue())
                    .resume(resume)
                    .build());
        }
    }

    private void syncExperiences(Resume resume, List<WorkExperienceInfoDto> experiences) {
        resume.getExperiences().clear();
        if (experiences == null) {
            return;
        }
        for (WorkExperienceInfoDto d : experiences) {
            resume.getExperiences().add(WorkExperienceInfo.builder()
                    .companyName(d.getCompanyName())
                    .position(d.getPosition())
                    .period(d.getPeriod())
                    .responsibilities(d.getResponsibilities())
                    .resume(resume)
                    .build());
        }
    }

    private void syncEducations(Resume resume, List<EducationInfoDto> educations) {
        resume.getEducations().clear();
        if (educations == null) {
            return;
        }
        for (EducationInfoDto d : educations) {
            resume.getEducations().add(EducationInfo.builder()
                    .enrollmentYear(d.getEnrollmentYear())
                    .institution(d.getInstitution())
                    .faculty(d.getFaculty())
                    .graduationYear(d.getGraduationYear())
                    .resume(resume)
                    .build());

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
            dto.setCategoryName(resume.getCategory().getName());
        }

        dto.setTitle(resume.getTitle());
        dto.setApplicantName(resume.getApplicantName());
        dto.setSkills(resume.getSkills());

        if (resume.getSalary() != null) {
            dto.setExpectedSalary(resume.getSalary().doubleValue());
        }

        dto.setCreatedDate(resume.getCreatedDate());

        dto.setContactInfos(resume.getContactInfos().stream().map(c -> {
            ContactInfoDto d = new ContactInfoDto();
            d.setContactTypeId(c.getContactType().getId());
            d.setTypeName(c.getContactType().getTypeName());
            d.setValue(c.getValue());
            return d;
        }).toList());

        dto.setExperiences(resume.getExperiences().stream().map(e -> {
            WorkExperienceInfoDto d = new WorkExperienceInfoDto();
            d.setCompanyName(e.getCompanyName());
            d.setPosition(e.getPosition());
            d.setPeriod(e.getPeriod());
            d.setResponsibilities(e.getResponsibilities());
            return d;
        }).toList());

        dto.setEducations(resume.getEducations().stream().map(e -> {
            EducationInfoDto d = new EducationInfoDto();
            d.setInstitution(e.getInstitution());
            d.setFaculty(e.getFaculty());
            d.setEnrollmentYear(e.getEnrollmentYear());
            d.setGraduationYear(e.getGraduationYear());

            return d;
        }).toList());

        return dto;
    }
}