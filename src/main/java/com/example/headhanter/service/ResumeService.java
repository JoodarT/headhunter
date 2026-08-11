package com.example.headhanter.service;

import com.example.headhanter.dao.ResumeDao;
import com.example.headhanter.dto.request.ContactsInfoDto;
import com.example.headhanter.dto.request.EducationInfoDto;
import com.example.headhanter.dto.request.ResumeCreateDto;
import com.example.headhanter.dto.request.WorkExperienceInfoDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.models.ContactsInfo;
import com.example.headhanter.models.EducationInfo;
import com.example.headhanter.models.Resume;
import com.example.headhanter.models.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeDao resumeDao;

    public ResumeResponseDto createResume(ResumeCreateDto dto) {
        Resume resume = new Resume();
        mapDtoToEntity(dto, resume);
        resume.setUpdateTime(LocalDateTime.now());

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
            throw new NoSuchElementException("Резюме с ID " + id + " не найдено");
        }
        return mapToDto(resume);
    }

    public List<ResumeResponseDto> getResumesByUserId(Long userId) {
        return resumeDao.findByUserId(userId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public ResumeResponseDto updateResume(Long id, ResumeCreateDto dto, Long currentUserId) {
        Resume existingResume = resumeDao.findById(id);
        if (existingResume == null) {
            throw new NoSuchElementException("Резюме с ID " + id + " не найдено");
        }

        if (existingResume.getUserId() == null || !existingResume.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("У вас нет прав на редактирование этого резюме");
        }

        mapDtoToEntity(dto, existingResume);
        existingResume.setUpdateTime(LocalDateTime.now());

        resumeDao.update(existingResume);
        return mapToDto(existingResume);
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

    public boolean deleteResume(Long id, Long currentUserId) {
        Resume existingResume = resumeDao.findById(id);
        if (existingResume == null) {
            throw new NoSuchElementException("Резюме с ID " + id + " не найдено");
        }

        if (existingResume.getUserId() == null || !existingResume.getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("У вас нет прав на удаление этого резюме");
        }

        resumeDao.deleteById(id);
        return true;
    }

    private void mapDtoToEntity(ResumeCreateDto dto, Resume resume) {
        resume.setUserId(dto.getUserId());
        resume.setApplicantName(dto.getApplicantName());
        resume.setTitle(dto.getTitle());
        resume.setCategory(dto.getCategory());
        resume.setSkills(dto.getSkills());
        resume.setExpectedSalary(dto.getExpectedSalary());

        if (dto.getContactInfo() != null) {
            ContactsInfo contactInfo = new ContactsInfo();
            contactInfo.setPhone(dto.getContactInfo().getPhone());
            contactInfo.setEmail(dto.getContactInfo().getEmail());
            contactInfo.setTelegram(dto.getContactInfo().getTelegram());
            contactInfo.setLinkedin(dto.getContactInfo().getLinkedin());
            resume.setContactInfo(contactInfo);
        }

        if (dto.getExperiences() != null) {
            List<WorkExperienceInfo> experiences = dto.getExperiences().stream().map(eDto -> {
                WorkExperienceInfo exp = new WorkExperienceInfo();
                exp.setCompanyName(eDto.getCompanyName());
                exp.setPosition(eDto.getPosition());
                exp.setPeriod(eDto.getPeriod());
                exp.setResponsibilities(eDto.getResponsibilities());
                return exp;
            }).toList();
            resume.setExperiences(experiences);
        }

        if (dto.getEducations() != null) {
            List<EducationInfo> educations = dto.getEducations().stream().map(eDto -> {
                EducationInfo edu = new EducationInfo();
                edu.setInstitution(eDto.getInstitution());
                edu.setFaculty(eDto.getFaculty());
                edu.setGraduationYear(eDto.getGraduationYear());
                return edu;
            }).toList();
            resume.setEducations(educations);
        }
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
        dto.setUpdateTime(resume.getUpdateTime());

        if (resume.getContactInfo() != null) {
            ContactsInfoDto cDto = new ContactsInfoDto();
            cDto.setPhone(resume.getContactInfo().getPhone());
            cDto.setEmail(resume.getContactInfo().getEmail());
            cDto.setTelegram(resume.getContactInfo().getTelegram());
            cDto.setLinkedin(resume.getContactInfo().getLinkedin());
            dto.setContactInfo(cDto);
        }

        if (resume.getExperiences() != null) {
            List<WorkExperienceInfoDto> expDtos = resume.getExperiences().stream().map(exp -> {
                WorkExperienceInfoDto eDto = new WorkExperienceInfoDto();
                eDto.setCompanyName(exp.getCompanyName());
                eDto.setPosition(exp.getPosition());
                eDto.setPeriod(exp.getPeriod());
                eDto.setResponsibilities(exp.getResponsibilities());
                return eDto;
            }).toList();
            dto.setExperiences(expDtos);
        } else {
            dto.setExperiences(Collections.emptyList());
        }

        if (resume.getEducations() != null) {
            List<EducationInfoDto> eduDtos = resume.getEducations().stream().map(edu -> {
                EducationInfoDto eDto = new EducationInfoDto();
                eDto.setInstitution(edu.getInstitution());
                eDto.setFaculty(edu.getFaculty());
                eDto.setGraduationYear(edu.getGraduationYear());
                return eDto;
            }).toList();
            dto.setEducations(eduDtos);
        } else {
            dto.setEducations(Collections.emptyList());
        }

        return dto;
    }
}