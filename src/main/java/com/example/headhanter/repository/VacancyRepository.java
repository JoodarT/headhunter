package com.example.headhanter.repository;

import com.example.headhanter.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByIsActiveTrue();
    List<Vacancy> findByEmployerId(Long employerId);
}