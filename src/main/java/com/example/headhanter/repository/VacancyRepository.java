package com.example.headhanter.repository;

import com.example.headhanter.models.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByIsActiveTrue();
    List<Vacancy> findByEmployerId(Long employerId);
    Page<Vacancy> findByIsActiveTrue(Pageable pageable);

    @Query(value = "SELECT v FROM Vacancy v LEFT JOIN RespondedApplicant ra ON ra.vacancy = v " +
            "WHERE v.isActive = true GROUP BY v ORDER BY COUNT(ra) DESC",
            countQuery = "SELECT COUNT(v) FROM Vacancy v WHERE v.isActive = true")
    Page<Vacancy> findAllActiveOrderByResponsesDesc(Pageable pageable);

    @Query(value = "SELECT v FROM Vacancy v LEFT JOIN RespondedApplicant ra ON ra.vacancy = v " +
            "WHERE v.isActive = true GROUP BY v ORDER BY COUNT(ra) ASC",
            countQuery = "SELECT COUNT(v) FROM Vacancy v WHERE v.isActive = true")
    Page<Vacancy> findAllActiveOrderByResponsesAsc(Pageable pageable);

    @Query("SELECT COUNT(ra) FROM RespondedApplicant ra WHERE ra.vacancy.id = :vacancyId")
    long countResponsesByVacancyId(@Param("vacancyId") Long vacancyId);
}