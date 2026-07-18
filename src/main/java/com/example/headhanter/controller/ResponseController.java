package com.example.headhanter.controller;

import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.models.Vacancy;
import com.example.headhanter.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responses")
@RequiredArgsConstructor
public class ResponseController {

    private final ResponseService responseService;

    @GetMapping("/by-vacancy/{vacancyId}")
    public List<RespondedApplicant> getResponsesByVacancy(@PathVariable Long vacancyId) {
        return responseService.getResponsesByVacancyId(vacancyId);
    }

    @GetMapping("/my-vacancies/{resumeId}")
    public List<Vacancy> getMyRespondedVacancies(@PathVariable Long resumeId) {
        return responseService.getVacanciesRespondedByResume(resumeId);
    }

    @PostMapping
    public ResponseEntity<Void> respond(@RequestParam Long resumeId, @RequestParam Long vacancyId) {
        responseService.respondToVacancy(resumeId, vacancyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<RespondedApplicant> getAll() {
        return responseService.getAllResponses();
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable Long id, @RequestParam boolean status) {
        boolean updated = responseService.updateConfirmation(id, status);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}