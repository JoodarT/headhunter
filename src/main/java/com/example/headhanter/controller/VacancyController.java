package com.example.headhanter.controller;

import com.example.headhanter.models.Vacancy;
import com.example.headhanter.models.Category;
import com.example.headhanter.models.RespondedApplicant;
import com.example.headhanter.service.VacancyService;
import com.example.headhanter.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private ResponseService responseService;

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy created = vacancyService.save(vacancy);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable Long id, @RequestBody Vacancy vacancy) {
        return vacancyService.update(id, vacancy)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        if (vacancyService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAllVacancies());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Vacancy>> getByCategory(@PathVariable Category category) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(category));
    }

    @GetMapping("/{id}/responses")
    public ResponseEntity<List<RespondedApplicant>> getResponses(@PathVariable Long id) {
        return ResponseEntity.ok(responseService.getResponsesForVacancy(id));
    }
}