package com.example.headhanter.controller;

import com.example.headhanter.models.Vacancy;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy created = vacancyService.createVacancy(vacancy);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Vacancy> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Long id) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        if (vacancy != null) {
            return ResponseEntity.ok(vacancy);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(@PathVariable Long id, @RequestBody Vacancy updatedVacancy) {
        Vacancy vacancy = vacancyService.updateVacancy(id, updatedVacancy);
        if (vacancy != null) {
            return ResponseEntity.ok(vacancy);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        boolean deleted = vacancyService.deleteVacancy(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}