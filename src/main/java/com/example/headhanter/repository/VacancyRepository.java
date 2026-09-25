package com.example.headhanter.repository;

import com.example.headhanter.models.Vacancy;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByIsActiveTrue();
    Page<Vacancy> findByIsActiveTrue(Pageable pageable);

    @Query(value = "SELECT v FROM Vacancy v WHERE v.isActive = true " +
            "AND (:categoryId IS NULL OR v.category.id = :categoryId)",
            countQuery = "SELECT COUNT(v) FROM Vacancy v WHERE v.isActive = true " +
            "AND (:categoryId IS NULL OR v.category.id = :categoryId)")
    Page<Vacancy> findActive(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT v FROM Vacancy v LEFT JOIN RespondedApplicant ra ON ra.vacancy = v " +
            "WHERE v.isActive = true AND (:categoryId IS NULL OR v.category.id = :categoryId) " +
            "GROUP BY v ORDER BY COUNT(ra) DESC, v.id DESC",
            countQuery = "SELECT COUNT(v) FROM Vacancy v WHERE v.isActive = true " +
            "AND (:categoryId IS NULL OR v.category.id = :categoryId)")
    Page<Vacancy> findAllActiveOrderByResponsesDesc(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT v FROM Vacancy v LEFT JOIN RespondedApplicant ra ON ra.vacancy = v " +
            "WHERE v.isActive = true AND (:categoryId IS NULL OR v.category.id = :categoryId) " +
            "GROUP BY v ORDER BY COUNT(ra) ASC, v.id DESC",
            countQuery = "SELECT COUNT(v) FROM Vacancy v WHERE v.isActive = true " +
            "AND (:categoryId IS NULL OR v.category.id = :categoryId)")
    Page<Vacancy> findAllActiveOrderByResponsesAsc(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT COUNT(ra) FROM RespondedApplicant ra WHERE ra.vacancy.id = :vacancyId")
    long countResponsesByVacancyId(@Param("vacancyId") Long vacancyId);


    @Query(value = " SELECT v FROM Vacancy v LEFT JOIN v.category c WHERE " +
            "LOWER (v.title) LIKE  LOWER (CONCAT('%', :keyword,'%')) OR " +
            "LOWER (CAST(v.description AS string)) LIKE  LOWER (CONCAT('%', :keyword,'%')) OR " +
            "LOWER(c.name) LIKE LOWER (CONCAT('%', :keyword, '%'))",
            countQuery = " SELECT COUNT(v) FROM Vacancy v LEFT JOIN v.category c WHERE " +
            "LOWER (v.title) LIKE  LOWER (CONCAT('%', :keyword,'%')) OR " +
            "LOWER (CAST(v.description AS string)) LIKE  LOWER (CONCAT('%', :keyword,'%')) OR " +
            "LOWER(c.name) LIKE LOWER (CONCAT('%', :keyword, '%'))")
    Page<Vacancy> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<Vacancy> findByEmployerIdAndIsActiveTrue(Long employerId);

    Page<Vacancy> findByCompanyAndIsActiveTrue(String company, Pageable pageable);

    @Query("SELECT v.company, COUNT(v) FROM Vacancy v " +
            "WHERE v.isActive = true AND v.company IS NOT NULL AND v.company <> '' " +
            "GROUP BY v.company ORDER BY COUNT(v) DESC")
    List<Object[]> countActiveVacanciesGroupedByCompany();

}