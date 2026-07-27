package com.example.headhanter.controller;

import com.example.headhanter.dto.ResumeCreateDto;
import com.example.headhanter.dto.ResumeResponseDto;
import com.example.headhanter.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeResponseDto> createResume(@Valid @RequestBody ResumeCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeService.createResume(dto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResumeResponseDto>> searchResumes(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword
    ) {
        return ResponseEntity.ok(resumeService.searchResumes(keyword));
    }

    @GetMapping("/category")
    public ResponseEntity<List<ResumeResponseDto>> getResumesByCategory(
            @RequestParam(name = "category", required = false) String category
    ) {
        return ResponseEntity.ok(resumeService.getResumesByCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponseDto>> getAllResumes() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponseDto> getResumeById(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeResponseDto> updateResume(
            @PathVariable Long id,
            @Valid @RequestBody ResumeCreateDto dto
    ) {
        return ResponseEntity.ok(resumeService.updateResume(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }
}