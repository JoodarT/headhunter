package com.example.headhanter.service;

import com.example.headhanter.dao.ResumeDao;
import com.example.headhanter.models.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeDao resumeDao;

    public Resume createResume(Resume resume) {
        return resumeDao.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeDao.findAll();
    }

    public Resume getResumeById(Long id) {
        return resumeDao.findById(id);
    }

    public Resume updateResume(Long id, Resume updatedResume) {
        Resume existingResume = resumeDao.findById(id);
        if (existingResume != null) {
            existingResume.setApplicantName(updatedResume.getApplicantName());
            existingResume.setTitle(updatedResume.getTitle());
            existingResume.setCategory(updatedResume.getCategory());
            existingResume.setSkills(updatedResume.getSkills());
            existingResume.setExpectedSalary(updatedResume.getExpectedSalary());

            resumeDao.update(existingResume);
            return existingResume;
        }
        return null;
    }
    public List<Resume> searchResumes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return resumeDao.findAll();
        }
        return resumeDao.searchResumes(keyword);
    }



    public boolean deleteResume(Long id) {
        if (resumeDao.findById(id) != null) {
            resumeDao.deleteById(id);
            return true;
        }
        return false;
    }
}