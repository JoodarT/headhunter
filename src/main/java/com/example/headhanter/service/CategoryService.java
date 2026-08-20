package com.example.headhanter.service;

import com.example.headhanter.models.Category;
import com.example.headhanter.models.Resume;
import com.example.headhanter.models.Vacancy;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getById(Long id);
    List<Vacancy> getActiveVacanciesByCategory(Long categoryId);
    List<Resume> getActiveResumesByCategory(Long categoryId);
}
