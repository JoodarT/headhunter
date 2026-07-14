package com.example.headhanter.controller;

import com.example.headhanter.models.Vacancy;
import com.example.headhanter.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private static final List<Vacancy> vacancies = new ArrayList<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public static Vacancy findVacancyById(Long id) {
        return vacancies.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        User employer = UserController.findUserById(vacancy.getEmployerId());
        if (employer == null) {
            return ResponseEntity.badRequest().build();
        }

        vacancy.setId(idGenerator.getAndIncrement());
        vacancies.add(vacancy);
        return new ResponseEntity<>(vacancy, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Vacancy> getAllVacancies() {
        return vacancies;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancyById(@PathVariable Long id) {
        Vacancy vacancy = findVacancyById(id);
        if (vacancy != null) {
            return ResponseEntity.ok(vacancy);
        }
        return ResponseEntity.notFound().build();
    }
}