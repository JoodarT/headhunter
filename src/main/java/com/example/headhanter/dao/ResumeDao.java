package com.example.headhanter.dao;

import com.example.headhanter.models.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Resume> findByCategoryId(Long categoryId) {
        String sql = "SELECT * FROM resumes WHERE category_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryId);
    }

    public List<Resume> findByUserId(Long userId) {
        String sql = "SELECT * FROM resumes WHERE user_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), userId);
    }

    public Resume save(Resume resume) {
        String sql = "INSERT INTO resumes (user_id, category_id, title, salary, is_active, created_date) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, resume.getUserId());
            ps.setObject(2, resume.getCategoryId());
            ps.setString(3, resume.getTitle());
            ps.setBigDecimal(4, resume.getSalary());
            ps.setBoolean(5, resume.getIsActive() != null ? resume.getIsActive() : true);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            resume.setId(keyHolder.getKey().longValue());
        }

        return resume;
    }

    public List<Resume> searchResumes(String keyword) {
        String sql = "SELECT * FROM resumes WHERE title LIKE ?";
        String searchPattern = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), searchPattern);
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
        String sql = "UPDATE resumes SET category_id = ?, title = ?, salary = ?, is_active = ? WHERE id = ?";
        jdbcTemplate.update(sql, resume.getCategoryId(), resume.getTitle(), resume.getSalary(), resume.getIsActive(), resume.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM resumes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}