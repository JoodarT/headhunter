package com.example.headhanter.service;

import com.example.headhanter.models.Resume;
import com.example.headhanter.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeService {
    private final List<Resume> resumes = new ArrayList<>();
    private long currentId = 1;

    @Autowired
    private VacancyService vacancyService;

    public Resume createResume(Resume resume) {
        resume.setId(currentId++);
        resumes.add(resume);
        return resume;
    }

    public Optional<Resume> updateResume(Long id, Resume updated) {
        return resumes.stream()
                .filter(r -> r.getId().equals(id))
                .peek(r -> {
                    r.setTitle(updated.getTitle());
                    r.setExpectedSalary(updated.getExpectedSalary());
                    r.setEducationList(updated.getEducationList());
                    r.setWorkExperienceList(updated.getWorkExperienceList());
                })
                .findFirst();
    }

    public boolean deleteResume(Long id) {
        return resumes.removeIf(r -> r.getId().equals(id));
    }

    public List<Resume> getAllResumes() {
        return new ArrayList<>(resumes);
    }

    public List<Resume> getResumesByCategory(Category category) {
        return resumes.stream()
                .filter(r -> vacancyService.getAllVacancies().stream()
                        .anyMatch(v -> v.getCategory() == category))
                .toList();
    }
}