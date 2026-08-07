package com.example.headhanter.controller.api;

import com.example.headhanter.dto.request.ResumeCreateDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeResponseDto> createResume(@Valid @RequestBody ResumeCreateDto dto) {
        ResumeResponseDto response = resumeService.createResume(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResumeResponseDto>> searchResumes(@RequestParam String keyword) {
        List<ResumeResponseDto> response = resumeService.searchResumes(keyword);
        return ResponseEntity.ok(response);
    }
}