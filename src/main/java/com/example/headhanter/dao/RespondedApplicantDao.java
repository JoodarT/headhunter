package com.example.headhanter.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RespondedApplicantDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findByVacancyIdRaw(Long vacancyId) {
        String sql = "SELECT * FROM responded_applicants WHERE vacancy_id = ?";
        return jdbcTemplate.queryForList(sql, vacancyId);
    }

    public void save(Long resumeId, Long vacancyId) {
        String sql = "INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation) VALUES (?, ?, false)";
        jdbcTemplate.update(sql, resumeId, vacancyId);
    }

    public List<Map<String, Object>> findByResumeIdRaw(Long resumeId) {
        String sql = "SELECT * FROM responded_applicants WHERE resume_id = ?";
        return jdbcTemplate.queryForList(sql, resumeId);
    }

    public List<Map<String, Object>> findAllRaw() {
        String sql = "SELECT * FROM responded_applicants";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> findByIdRaw(Long id) {
        String sql = "SELECT * FROM responded_applicants WHERE id = ?";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, id);
        return results.isEmpty() ? null : results.get(0);
    }

    public void updateConfirmation(Long id, boolean confirmation) {
        String sql = "UPDATE responded_applicants SET confirmation = ? WHERE id = ?";
        jdbcTemplate.update(sql, confirmation, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM responded_applicants WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}