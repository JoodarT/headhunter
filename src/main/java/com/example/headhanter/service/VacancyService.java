package com.example.headhanter.service;

import com.example.headhanter.dao.VacancyDao;
import com.example.headhanter.models.Vacancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyDao vacancyDao;

    public Vacancy createVacancy(Vacancy vacancy) {
        log.info("Попытка создания вакансии: название='{}', ID работодателя={}",
                vacancy.getTitle(), vacancy.getEmployerId());

        Vacancy createdVacancy = vacancyDao.save(vacancy);

        log.debug("Вакансия успешно сохранена в БД с ID: {}", createdVacancy.getId());
        return createdVacancy;
    }

    public List<Vacancy> getAllVacancies() {
        log.info("Запрос на получение всех вакансий");
        List<Vacancy> vacancies = vacancyDao.findAll();
        log.debug("Найдено всего вакансий: {}", vacancies.size());
        return vacancies;
    }

    public Vacancy getVacancyById(Long id) {
        log.info("Запрос на просмотр вакансии с ID: {}", id);
        return vacancyDao.findById(id);
    }

    public Vacancy updateVacancy(Long id, Vacancy updatedVacancy) {
        log.info("Запрос на обновление вакансии с ID: {}", id);
        Vacancy existingVacancy = vacancyDao.findWithoutIncrementingViews(id);

        if (existingVacancy != null) {
            existingVacancy.setTitle(updatedVacancy.getTitle());
            existingVacancy.setDescription(updatedVacancy.getDescription());
            existingVacancy.setSalary(updatedVacancy.getSalary());
            existingVacancy.setCategory(updatedVacancy.getCategory());
            existingVacancy.setEmployerId(updatedVacancy.getEmployerId());

            vacancyDao.update(existingVacancy);
            log.debug("Вакансия с ID: {} успешно обновлена", id);
            return existingVacancy;
        }

        log.warn("Не удалось обновить вакансию: вакансия с ID: {} не найдена", id);
        return null;
    }

    public List<Vacancy> getVacanciesByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            log.info("Запрос вакансий по пустой категории, возвращаем все вакансии");
            return vacancyDao.findAll();
        }
        log.info("Запрос вакансий по категории: {}", category);
        return vacancyDao.findByCategory(category);
    }

    public List<Vacancy> getVacanciesByMinSalary(Double minSalary) {
        if (minSalary == null || minSalary < 0) {
            log.info("Запрос вакансий по некорректной мин. зарплате ({}), возвращаем все", minSalary);
            return vacancyDao.findAll();
        }
        log.info("Запрос вакансий с минимальной зарплатой: {}", minSalary);
        return vacancyDao.findByMinSalary(minSalary);
    }

    public boolean deleteVacancy(Long id) {
        log.info("Запрос на удаление вакансии с ID: {}", id);
        if (vacancyDao.findWithoutIncrementingViews(id) != null) {
            vacancyDao.deleteById(id);
            log.debug("Вакансия с ID: {} успешно удалена", id);
            return true;
        }
        log.warn("Не удалось удалить вакансию: вакансия с ID: {} не найдена", id);
        return false;
    }
}