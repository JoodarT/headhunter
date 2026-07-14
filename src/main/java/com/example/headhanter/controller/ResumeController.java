package com.example.headhanter.controller;

import com.example.headhanter.models.Resume;
import com.example.headhanter.models.Category;
import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResponseService responseService;

    @PostMapping
    public ResponseEntity<Resume> createResume(@RequestBody Resume resume) {
        Resume created = resumeService.save(resume);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resume> updateResume(@PathVariable Long id, @RequestBody Resume resume) {
        return resumeService.update(id, resume)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        if (resumeService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Resume>> getByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(resumeService.getResumesByCategory(category));
    }

    @PostMapping("/apply")
    public ResponseEntity<RespondedApplicant> apply(
            @RequestParam Long vacancyId,
            @RequestParam Long resumeId) {
        RespondedApplicant response = responseService.applyToVacancy(vacancyId, resumeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}