package com.example.headhanter.dao;

import com.example.headhanter.models.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VacancyDao {

    private final JdbcTemplate jdbcTemplate;

    public Vacancy save(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies (title, description, salary, category) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, vacancy.getTitle(), vacancy.getDescription(), vacancy.getSalary(), vacancy.getCategory());

        String selectSql = "SELECT * FROM vacancies ORDER BY id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectSql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> findAll() {
        String sql = "SELECT * FROM vacancies";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
    }

//    public Vacancy findById(Long id) {
//        String sql = "SELECT * FROM vacancies WHERE id = ?";
//        try {
//            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
    public List<Vacancy> findByCategory(String category) {
        String sql = "SELECT * FROM vacancies WHERE category = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), category);
    }

    public void update(Vacancy vacancy) {
        String sql = "UPDATE vacancies SET title = ?, description = ?, salary = ?, category = ? WHERE id = ?";
        jdbcTemplate.update(sql, vacancy.getTitle(), vacancy.getDescription(), vacancy.getSalary(), vacancy.getCategory(), vacancy.getId());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM vacancies WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Vacancy findById(Long id) {
        String updateSql = "UPDATE vacancies SET views = views + 1 WHERE id = ?";
        jdbcTemplate.update(updateSql, id);

        String selectSql = "SELECT * FROM vacancies WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(selectSql, new BeanPropertyRowMapper<>(Vacancy.class), id);
        } catch (Exception e) {
            return null;
        }
    }
}