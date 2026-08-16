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
        resume.setApplicantName(dto.getApplicantName());
        resume.setSkills(dto.getSkills());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new NoSuchElementException("Категория с id " + dto.getCategoryId() + " не найдена"));
            resume.setCategory(category);
        }

        if (dto.getExpectedSalary() != null) {
            resume.setSalary(BigDecimal.valueOf(dto.getExpectedSalary()));
        }

        if (dto.getContactInfo() != null) {
            ContactsInfoDto c = dto.getContactInfo();
            resume.setContactInfo(ContactsInfo.builder()
                    .phone(c.getPhone())
                    .email(c.getEmail())
                    .telegram(c.getTelegram())
                    .linkedin(c.getLinkedin())
                    .build());
        }

        syncExperiences(resume, dto.getExperiences());
        syncEducations(resume, dto.getEducations());
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

        if (resume.getContactInfo() != null) {
            ContactsInfo c = resume.getContactInfo();
            ContactsInfoDto contactDto = new ContactsInfoDto();
            contactDto.setPhone(c.getPhone());
            contactDto.setEmail(c.getEmail());
            contactDto.setTelegram(c.getTelegram());
            contactDto.setLinkedin(c.getLinkedin());
            dto.setContactInfo(contactDto);
        }

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
            d.setGraduationYear(e.getGraduationYear());
            return d;
        }).toList());

        return dto;
    }
}