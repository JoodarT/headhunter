package com.example.headhanter.service;

import com.example.headhanter.dao.VacancyDao;
import com.example.headhanter.dto.VacancyCreateDto;
import com.example.headhanter.dto.VacancyResponseDto;
import com.example.headhanter.models.Vacancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyDao vacancyDao;

    public VacancyResponseDto create(VacancyCreateDto dto) {
        log.info("Попытка создания вакансии: название='{}', ID работодателя={}",
                dto.getTitle(), dto.getEmployerId());

        Vacancy vacancy = new Vacancy();
        vacancy.setEmployerId(dto.getEmployerId());
        vacancy.setTitle(dto.getTitle());
        vacancy.setDescription(dto.getDescription());
        vacancy.setSalary(dto.getSalary());
        vacancy.setCategoryId(dto.getCategoryId());
        vacancy.setViews(0);

        Vacancy savedVacancy = vacancyDao.save(vacancy);
        log.debug("Вакансия успешно сохранена в БД с ID: {}", savedVacancy.getId());

        return mapToResponseDto(savedVacancy);
    }

    public List<VacancyResponseDto> getRespondedVacanciesByUser(Long userId) {
        List<Vacancy> vacancies = vacancyDao.findRespondedVacanciesByUserId(userId);

        return vacancies.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public List<VacancyResponseDto> getAll() {
        log.info("Запрос на получение всех вакансий");
        List<Vacancy> vacancies = vacancyDao.findAll();
        log.debug("Найдено всего вакансий: {}", vacancies.size());

        return vacancies.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public VacancyResponseDto getById(Long id) {
        log.info("Запрос на просмотр вакансии с ID: {}", id);
        Vacancy vacancy = vacancyDao.findById(id);
        if (vacancy == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вакансия не найдена");
        }
        return mapToResponseDto(vacancy);
    }

    public VacancyResponseDto update(Long id, VacancyCreateDto dto) {
        log.info("Запрос на обновление вакансии с ID: {}", id);
        Vacancy existingVacancy = vacancyDao.findWithoutIncrementingViews(id);

        if (existingVacancy == null) {
            log.warn("Не удалось обновить вакансию: вакансия с ID: {} не найдена", id);
            throw new NoSuchElementException("Вакансия с ID: " + id + " не найдена"); // Используем NoSuchElementException для нашего GlobalExceptionHandler
        }

        existingVacancy.setTitle(dto.getTitle());
        existingVacancy.setDescription(dto.getDescription());
        existingVacancy.setSalary(dto.getSalary());
        existingVacancy.setCategoryId(dto.getCategoryId());
        existingVacancy.setEmployerId(dto.getEmployerId());

        vacancyDao.update(existingVacancy);
        log.debug("Вакансия с ID: {} успешно обновлена", id);
        return mapToResponseDto(existingVacancy);
    }

    public void delete(Long id) {
        log.info("Запрос на удаление вакансии с ID: {}", id);
        if (vacancyDao.findWithoutIncrementingViews(id) == null) {
            log.warn("Не удалось удалить вакансию: вакансия с ID: {} не найдена", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Вакансия не найдена");
        }
        vacancyDao.deleteById(id);
        log.debug("Вакансия с ID: {} успешно удалена", id);
    }

    private VacancyResponseDto mapToResponseDto(Vacancy vacancy) {
        if (vacancy == null) return null;
        VacancyResponseDto dto = new VacancyResponseDto();
        dto.setId(vacancy.getId());
        dto.setEmployerId(vacancy.getEmployerId());
        dto.setTitle(vacancy.getTitle());
        dto.setDescription(vacancy.getDescription());
        dto.setSalary(vacancy.getSalary());
        dto.setCategory(vacancy.getCategoryId());
        dto.setViews(vacancy.getViews());
        return dto;
    }
}