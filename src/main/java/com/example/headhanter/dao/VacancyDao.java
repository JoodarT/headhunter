package com.example.headhanter.dao;

import com.example.headhanter.models.Vacancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Repository
@RequiredArgsConstructor
public class VacancyDao {

    private final JdbcTemplate jdbcTemplate;

    public Vacancy save(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies (title, description, salary, category_id, views, employer_id, is_active, update_time) VALUES (?, ?, ?, ?, 0, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vacancy.getTitle());
            ps.setString(2, vacancy.getDescription());
            ps.setBigDecimal(3, vacancy.getSalary());
            ps.setObject(4, vacancy.getCategoryId());
            ps.setObject(5, vacancy.getEmployerId());
            ps.setBoolean(6, vacancy.getIsActive() != null ? vacancy.getIsActive() : true);
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
        log.debug("Выполнен INSERT для вакансии. Сгенерирован ID: {}", generatedId);

        return findWithoutIncrementingViews(generatedId);
    }

    public List<Vacancy> findAll() {
        String sql = "SELECT * FROM vacancies";
        List<Vacancy> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class));
        log.debug("Выполнен SELECT всех вакансий. Найдено записей: {}", result.size());
        return result;
    }

    public Vacancy findById(Long id) {
        log.debug("Выполняется инкремент просмотров (UPDATE) для вакансии с ID: {}", id);
        String updateSql = "UPDATE vacancies SET views = views + 1 WHERE id = ?";
        jdbcTemplate.update(updateSql, id);

        return findWithoutIncrementingViews(id);
    }

    public Vacancy findWithoutIncrementingViews(Long id) {
        String sql = "SELECT * FROM vacancies WHERE id = ?";
        try {
            Vacancy vacancy = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Vacancy.class), id);
            log.debug("Выполнен SELECT для ID: {}. Найдено: {}", id, vacancy != null);
            return vacancy;
        } catch (Exception e) {
            log.warn("Вакансия с ID: {} не найдена в базе данных.", id);
            return null;
        }
    }

    public List<Vacancy> findByCategoryId(Long categoryId) {
        String sql = "SELECT * FROM vacancies WHERE category_id = ?";
        List<Vacancy> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), categoryId);
        log.debug("Выполнен SELECT по категории ID '{}'. Найдено записей: {}", categoryId, result.size());
        return result;
    }

    public List<Vacancy> findRespondedVacanciesByUserId(Long userId) {
        String sql = """
            SELECT v.* 
            FROM vacancies v
            JOIN responded_applicants ra ON v.id = ra.vacancy_id
            JOIN resumes r ON ra.resume_id = r.id
            WHERE r.user_id = ?
        """;
        List<Vacancy> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacancy.class), userId);
        log.debug("Выполнен SELECT вакансий, на которые откликнулся юзер ID {}. Найдено: {}", userId, result.size());
        return result;
    }

    public void update(Vacancy vacancy) {
        String sql = "UPDATE vacancies SET title = ?, description = ?, salary = ?, category_id = ?, employer_id = ?, update_time = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(
                sql,
                vacancy.getTitle(),
                vacancy.getDescription(),
                vacancy.getSalary(),
                vacancy.getCategoryId(),
                vacancy.getEmployerId(),
                Timestamp.valueOf(LocalDateTime.now()),
                vacancy.getId()
        );
        log.debug("Выполнен UPDATE для вакансии с ID: {}. Изменено строк: {}", vacancy.getId(), rowsAffected);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM vacancies WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        log.debug("Выполнен DELETE для ID: {}. Удалено строк: {}", id, rowsAffected);
    }
}