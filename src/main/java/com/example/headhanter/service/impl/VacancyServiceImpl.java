package com.example.headhanter.service.impl;

import com.example.headhanter.dao.UserDao;
import com.example.headhanter.dao.VacancyDao;
import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.Vacancy;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyDao vacancyDao;
    private final UserDao userDao;

    @Override
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

    @Override
    public List<VacancyResponseDto> getAll() {
        log.info("Запрос на получение всех вакансий");
        List<Vacancy> vacancies = vacancyDao.findAll();
        log.debug("Найдено всего вакансий: {}", vacancies.size());

        return vacancies.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyResponseDto getById(Long id) {
        log.info("Запрос на просмотр вакансии с ID: {}", id);
        Vacancy vacancy = vacancyDao.findById(id);
        if (vacancy == null) {
            throw new NoSuchElementException("Вакансия с ID: " + id + " не найдена");
        }
        return mapToResponseDto(vacancy);
    }

    @Override
    public List<VacancyResponseDto> getRespondedVacanciesByUser(Long userId) {
        List<Vacancy> vacancies = vacancyDao.findRespondedVacanciesByUserId(userId);

        return vacancies.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public VacancyResponseDto update(Long id, VacancyCreateDto dto, Long currentUserId) {
        log.info("Запрос на обновление вакансии с ID: {} пользователем с ID: {}", id, currentUserId);
        Vacancy existingVacancy = vacancyDao.findWithoutIncrementingViews(id);

        if (existingVacancy == null) {
            log.warn("Не удалось обновить вакансию: вакансия с ID: {} не найдена", id);
            throw new NoSuchElementException("Вакансия с ID: " + id + " не найдена");
        }

        checkOwnership(existingVacancy.getEmployerId(), currentUserId);

        existingVacancy.setTitle(dto.getTitle());
        existingVacancy.setDescription(dto.getDescription());
        existingVacancy.setSalary(dto.getSalary());
        existingVacancy.setCategoryId(dto.getCategoryId());
        existingVacancy.setEmployerId(dto.getEmployerId());

        vacancyDao.update(existingVacancy);
        log.debug("Вакансия с ID: {} успешно обновлена", id);
        return mapToResponseDto(existingVacancy);
    }

    @Override
    public void delete(Long id, Long currentUserId) {
        log.info("Запрос на удаление вакансии с ID: {} пользователем с ID: {}", id, currentUserId);
        Vacancy existingVacancy = vacancyDao.findWithoutIncrementingViews(id);
        if (existingVacancy == null) {
            log.warn("Не удалось удалить вакансию: вакансия с ID: {} не найдена", id);
            throw new NoSuchElementException("Вакансия с ID: " + id + " не найдена");
        }

        checkOwnership(existingVacancy.getEmployerId(), currentUserId);

        vacancyDao.deleteById(id);
        log.debug("Вакансия с ID: {} успешно удалена", id);
    }

    private void checkOwnership(Long employerId, Long currentUserId) {
        if (currentUserId == null || employerId == null || !employerId.equals(currentUserId)) {
            throw new AccessDeniedException("Вы можете изменять или удалять только свои вакансии");
        }
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