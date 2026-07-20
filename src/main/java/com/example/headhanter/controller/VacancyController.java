package com.example.headhanter.controller;

import com.example.headhanter.dto.VacancyCreateDto;
import com.example.headhanter.dto.VacancyResponseDto;
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

    @PostMapping("/create")
    public ResponseEntity<VacancyResponseDto> createVacancy(@RequestBody VacancyCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<VacancyResponseDto>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacancyResponseDto> getVacancyById(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VacancyResponseDto> updateVacancy(@PathVariable Long id, @RequestBody VacancyCreateDto dto) {
        return ResponseEntity.ok(vacancyService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        vacancyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}