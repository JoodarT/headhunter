package com.example.headhanter.service.impl;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.Category;
import com.example.headhanter.models.User;
import com.example.headhanter.models.Vacancy;
import com.example.headhanter.repository.CategoryRepository;
import com.example.headhanter.repository.VacancyRepository;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    @Override
    public List<Vacancy> findAllActive() {
        return vacancyRepository.findByIsActiveTrue();
    }

    @Override
    public List<VacancyResponseDto> getAll() {
        return vacancyRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public VacancyResponseDto getById(Long id) {
        Vacancy vacancy = findById(id);
        return mapToResponseDto(vacancy);
    }

    @Override
    public List<VacancyResponseDto> getRespondedVacanciesByUser(Long userId) {
        return List.of();
    }

    @Override
    @Transactional
    public VacancyResponseDto create(VacancyCreateDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория с id " + dto.getCategoryId() + " не найдена"));

        Vacancy vacancy = Vacancy.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .salary(dto.getSalary())
                .category(category)
                .updateTime(LocalDateTime.now())
                .build();

        return mapToResponseDto(vacancyRepository.save(vacancy));
    }

    @Override
    @Transactional
    public VacancyResponseDto update(Long id, VacancyCreateDto dto, Long currentUserId) {
        Vacancy vacancy = findById(id);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Категория не найдена"));
            vacancy.setCategory(category);
        }

        vacancy.setTitle(dto.getTitle());
        vacancy.setDescription(dto.getDescription());
        vacancy.setSalary(dto.getSalary());
        vacancy.setUpdateTime(LocalDateTime.now());

        return mapToResponseDto(vacancyRepository.save(vacancy));
    }

    @Override
    @Transactional
    public void delete(Long id, Long currentUserId) {
        Vacancy vacancy = findById(id);
        vacancy.setIsActive(false);
        vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy findById(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Вакансия с id " + id + " не найдена"));
    }

    @Override
    @Transactional
    public Vacancy create(Vacancy vacancy, Long employerId, Long categoryId) {
        User employer = userService.getUserById(employerId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Категория с id " + categoryId + " не найдена"));

        vacancy.setEmployer(employer);
        vacancy.setCategory(category);
        vacancy.setUpdateTime(LocalDateTime.now());

        return vacancyRepository.save(vacancy);
    }

    @Override
    @Transactional
    public Vacancy update(Long id, Vacancy updatedVacancy, Long categoryId) {
        Vacancy vacancy = findById(id);

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Категория не найдена"));
            vacancy.setCategory(category);
        }

        vacancy.setTitle(updatedVacancy.getTitle());
        vacancy.setDescription(updatedVacancy.getDescription());
        vacancy.setSalary(updatedVacancy.getSalary());
        vacancy.setUpdateTime(LocalDateTime.now());

        return vacancyRepository.save(vacancy);
    }

    @Override
    @Transactional
    public void incrementViews(Long id) {
        Vacancy vacancy = findById(id);
        vacancy.setViews(vacancy.getViews() + 1);
        vacancyRepository.save(vacancy);
    }

    private VacancyResponseDto mapToResponseDto(Vacancy vacancy) {
        VacancyResponseDto dto = new VacancyResponseDto();
        dto.setId(vacancy.getId());
        dto.setTitle(vacancy.getTitle());
        dto.setDescription(vacancy.getDescription());
        dto.setSalary(vacancy.getSalary());
        dto.setViews(vacancy.getViews());
        if (vacancy.getCategory() != null) {
            dto.setCategoryId(vacancy.getCategory().getId());
        }
        if (vacancy.getEmployer() != null) {
            dto.setEmployerId(vacancy.getEmployer().getId());
        }
        return dto;
    }
}