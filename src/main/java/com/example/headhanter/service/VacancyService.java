package com.example.headhanter.service;

import com.example.headhanter.models.Vacancy;
import com.example.headhanter.models.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {
    private final List<Vacancy> vacancies = new ArrayList<>();
    private long currentId = 1;

    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(currentId++);
        vacancies.add(vacancy);
        return vacancy;
    }

    public Optional<Vacancy> update(Long id, Vacancy updated) {
        return vacancies.stream()
                .filter(v -> v.getId().equals(id))
                .peek(v -> {
                    v.setTitle(updated.getTitle());
                    v.setDescription(updated.getDescription());
                    v.setSalary(updated.getSalary());
                    v.setCategory(updated.getCategory());
                })
                .findFirst();
    }

    public boolean delete(Long id) {
        return vacancies.removeIf(v -> v.getId().equals(id));
    }

    public List<Vacancy> getAllVacancies() {
        return new ArrayList<>(vacancies);
    }

    public List<Vacancy> getVacanciesByCategory(Category category) {
        return vacancies.stream().filter(v -> v.getCategory() == category).toList();
    }
}