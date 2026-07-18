package com.example.headhanter.dao;

import com.example.headhanter.models.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeDao {

    private final JdbcTemplate jdbcTemplate;

    public Resume save(Resume resume) {
        String sql = "INSERT INTO resumes (applicant_name, title, category, skills, expected_salary) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, resume.getApplicantName(), resume.getTitle(), resume.getCategory(), resume.getSkills(), resume.getExpectedSalary());

        String selectSql = "SELECT * FROM resumes WHERE applicant_name = ? ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectSql, new BeanPropertyRowMapper<>(Resume.class), resume.getApplicantName());
    }

    public List<Resume> findAll() {
        String sql = "SELECT * FROM resumes";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class));
    }

    public Resume findById(Long id) {
        String sql = "SELECT * FROM resumes WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Resume.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Resume resume) {
        String sql = "UPDATE resumes SET applicant_name = ?, title = ?, category = ?, skills = ?, expected_salary = ? WHERE id = ?";
        jdbcTemplate.update(sql, resume.getApplicantName(), resume.getTitle(), resume.getCategory(), resume.getSkills(), resume.getExpectedSalary(), resume.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM resumes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}