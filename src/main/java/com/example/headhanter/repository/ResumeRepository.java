package com.example.headhanter.repository;

import com.example.headhanter.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUserId(Long userId);
    List<Resume> findByCategoryId(Long categoryId);
}