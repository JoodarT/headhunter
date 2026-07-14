package com.example.headhanter.controller;

import com.example.headhanter.models.Resume;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private static final List<Resume> resumes = new ArrayList<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public static Resume findResumeById(Long id) {
        return resumes.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume) {
        resume.setId(idGenerator.getAndIncrement());
        resumes.add(resume);
        return new ResponseEntity<>(resume, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Resume> getAllResumes() {
        return resumes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) {
        Resume resume = findResumeById(id);
        if (resume != null) {
            return ResponseEntity.ok(resume);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable Long id, @RequestBody Resume updatedResume) {
        Resume existingResume = findResumeById(id);
        if (existingResume == null) {
            return ResponseEntity.notFound().build();
        }
        existingResume.setApplicantName(updatedResume.getApplicantName());
        existingResume.setTitle(updatedResume.getTitle());
        existingResume.setSkills(updatedResume.getSkills());
        existingResume.setExpectedSalary(updatedResume.getExpectedSalary());

        return ResponseEntity.ok(existingResume);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        Resume resume = findResumeById(id);
        if (resume == null) {
            return ResponseEntity.notFound().build();
        }
        resumes.remove(resume);
        return ResponseEntity.noContent().build();
    }
}