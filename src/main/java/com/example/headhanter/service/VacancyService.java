package com.example.headhanter.service;

import com.example.headhanter.dao.VacancyDao;
import com.example.headhanter.dto.VacancyCreateDto;
import com.example.headhanter.dto.VacancyResponseDto;
import com.example.headhanter.models.Vacancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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
        vacancy.setCategory(dto.getCategory());
        vacancy.setViews(0);

        Vacancy savedVacancy = vacancyDao.save(vacancy);
        log.debug("Вакансия успешно сохранена в БД с ID: {}", savedVacancy.getId());

        return mapToResponseDto(savedVacancy);
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
        return mapToResponseDto(vacancy);
    }

    public List<VacancyResponseDto> getByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            log.info("Запрос вакансий по пустой категории, возвращаем все вакансии");
            return getAll();
        }
        log.info("Запрос вакансий по категории: {}", category);
        return vacancyDao.findByCategory(category).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<VacancyResponseDto> getByMinSalary(Double minSalary) {
        if (minSalary == null || minSalary < 0) {
            log.info("Запрос вакансий по некорректной мин. зарплате ({}), возвращаем все", minSalary);
            return getAll();
        }
        log.info("Запрос вакансий с минимальной зарплатой: {}", minSalary);
        return vacancyDao.findByMinSalary(minSalary).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public VacancyResponseDto update(Long id, VacancyCreateDto dto) {
        log.info("Запрос на обновление вакансии с ID: {}", id);
        Vacancy existingVacancy = vacancyDao.findWithoutIncrementingViews(id);

        if (existingVacancy != null) {
            existingVacancy.setTitle(dto.getTitle());
            existingVacancy.setDescription(dto.getDescription());
            existingVacancy.setSalary(dto.getSalary());
            existingVacancy.setCategory(dto.getCategory());
            existingVacancy.setEmployerId(dto.getEmployerId());

            vacancyDao.update(existingVacancy);
            log.debug("Вакансия с ID: {} успешно обновлена", id);
            return mapToResponseDto(existingVacancy);
        }

        log.warn("Не удалось обновить вакансию: вакансия с ID: {} не найдена", id);
        return null;
    }

    public boolean delete(Long id) {
        log.info("Запрос на удаление вакансии с ID: {}", id);
        if (vacancyDao.findWithoutIncrementingViews(id) != null) {
            vacancyDao.deleteById(id);
            log.debug("Вакансия с ID: {} успешно удалена", id);
            return true;
        }
        log.warn("Не удалось удалить вакансию: вакансия с ID: {} не найдена", id);
        return false;
    }

    private VacancyResponseDto mapToResponseDto(Vacancy vacancy) {
        if (vacancy == null) return null;
        VacancyResponseDto dto = new VacancyResponseDto();
        dto.setId(vacancy.getId());
        dto.setEmployerId(vacancy.getEmployerId());
        dto.setTitle(vacancy.getTitle());
        dto.setDescription(vacancy.getDescription());
        dto.setSalary(vacancy.getSalary());
        dto.setCategory(vacancy.getCategory());
        dto.setViews(vacancy.getViews());
        return dto;
    }
}