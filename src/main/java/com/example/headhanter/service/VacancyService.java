package com.example.headhanter.service;

import com.example.headhanter.dao.VacancyDao;
import com.example.headhanter.models.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyDao vacancyDao;

    public Vacancy createVacancy(Vacancy vacancy) {
        return vacancyDao.save(vacancy);
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyDao.findAll();
    }

    public Vacancy getVacancyById(Long id) {
        return vacancyDao.findById(id);
    }

    public Vacancy updateVacancy(Long id, Vacancy updatedVacancy) {
        Vacancy existingVacancy = vacancyDao.findById(id);
        if (existingVacancy != null) {
            existingVacancy.setTitle(updatedVacancy.getTitle());
            existingVacancy.setDescription(updatedVacancy.getDescription());
            existingVacancy.setSalary(updatedVacancy.getSalary());
            existingVacancy.setCategory(updatedVacancy.getCategory());

            vacancyDao.update(existingVacancy);
            return existingVacancy;
        }
        return null;
    }

    public List<Vacancy> getVacanciesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return vacancyDao.findAll();
        }
        return vacancyDao.findByCategory(category);
    }

    public boolean deleteVacancy(Long id) {
        if (vacancyDao.findById(id) != null) {
            vacancyDao.deleteById(id);
            return true;
        }
        return false;
    }
}