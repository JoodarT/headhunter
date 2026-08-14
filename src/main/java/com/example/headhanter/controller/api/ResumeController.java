package com.example.headhanter.controller.api;

import com.example.headhanter.dto.request.ResumeCreateDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResumeResponseDto> createResume(
            @Valid @RequestBody ResumeCreateDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        dto.setUserId(currentUser.getId());

        ResumeResponseDto createdResume = resumeService.createResume(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdResume);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeResponseDto> updateResume(
            @PathVariable("id") Long id,
            @Valid @RequestBody ResumeCreateDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        dto.setUserId(currentUser.getId());

        ResumeResponseDto updatedResume = resumeService.updateResume(id, dto, currentUser.getId());

        return ResponseEntity.ok(updatedResume);
    }
}