package com.example.headhanter.service;

import com.example.headhanter.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category getById(Long id);
}
