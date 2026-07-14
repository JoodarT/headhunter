package com.example.headhanter.controller;

import com.example.headhanter.models.Vacancy;
import com.example.headhanter.service.VacancyService;
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

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy created = vacancyService.save(vacancy);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        boolean deleted = vacancyService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}