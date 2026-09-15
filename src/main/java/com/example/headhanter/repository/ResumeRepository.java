package com.example.headhanter.repository;

import com.example.headhanter.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUserId(Long userId);

    @Query("SELECT r FROM Resume r LEFT JOIN r.category c WHERE r.isActive = true " +
            "AND (:categoryId IS NULL OR c.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR " +
            "     LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "     LOWER(r.skills) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "     LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Resume> findActive(@Param("categoryId") Long categoryId, @Param("keyword") String keyword);
}