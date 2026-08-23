package com.example.headhanter.service.impl;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.CompanyResponseDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.User;
import com.example.headhanter.models.Vacancy;
import com.example.headhanter.repository.VacancyRepository;
import com.example.headhanter.service.CategoryService;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public List<VacancyResponseDto> getAll() {
        return vacancyRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public Page<VacancyResponseDto> getAllPaged(int page, int size, boolean sortByResponses, boolean ascending) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1));

        Page<Vacancy> vacancies;
        if (sortByResponses) {
            vacancies = ascending
                    ? vacancyRepository.findAllActiveOrderByResponsesAsc(pageable)
                    : vacancyRepository.findAllActiveOrderByResponsesDesc(pageable);
        } else {
            vacancies = vacancyRepository.findByIsActiveTrue(pageable);
        }

        return vacancies.map(this::mapToResponseDto);
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
        User employer = userService.getUserById(dto.getEmployerId());

        Vacancy vacancy = Vacancy.builder()
                .title(dto.getTitle())
                .company(dto.getCompany())
                .description(dto.getDescription())
                .salary(dto.getSalary())
                .category(categoryService.getById(dto.getCategoryId()))
                .employer(employer)
                .views(0)
                .isActive(true)
                .updateTime(LocalDateTime.now())
                .build();

        return mapToResponseDto(vacancyRepository.save(vacancy));
    }

    @Override
    @Transactional
    public VacancyResponseDto update(Long id, VacancyCreateDto dto, Long currentUserId) {
        Vacancy vacancy = findById(id);
        assertOwner(vacancy, currentUserId);

        if (dto.getCategoryId() != null) {
            vacancy.setCategory(categoryService.getById(dto.getCategoryId()));
        }

        vacancy.setTitle(dto.getTitle());
        vacancy.setCompany(dto.getCompany());
        vacancy.setDescription(dto.getDescription());
        vacancy.setSalary(dto.getSalary());
        vacancy.setUpdateTime(LocalDateTime.now());

        return mapToResponseDto(vacancyRepository.save(vacancy));
    }

    @Override
    @Transactional
    public void delete(Long id, Long currentUserId) {
        Vacancy vacancy = findById(id);
        assertOwner(vacancy, currentUserId);
        vacancy.setIsActive(false);
        vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy findById(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Вакансия с id " + id + " не найдена"));
    }

    private void assertOwner(Vacancy vacancy, Long currentUserId) {
        if (vacancy.getEmployer() == null || !vacancy.getEmployer().getId().equals(currentUserId)) {
            throw new AccessDeniedException("Вы можете редактировать только свои вакансии");
        }
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
        dto.setCompany(vacancy.getCompany());
        dto.setDescription(vacancy.getDescription());
        dto.setSalary(vacancy.getSalary());
        dto.setViews(vacancy.getViews());
        dto.setIsActive(vacancy.getIsActive());
        dto.setUpdateTime(vacancy.getUpdateTime());
        if (vacancy.getCategory() != null) {
            dto.setCategoryId(vacancy.getCategory().getId());
            dto.setCategoryName(vacancy.getCategory().getName());
        }
        if (vacancy.getEmployer() != null) {
            dto.setEmployerId(vacancy.getEmployer().getId());
        }
        dto.setResponsesCount(vacancyRepository.countResponsesByVacancyId(vacancy.getId()));
        return dto;
    }

    @Override
    public List<VacancyResponseDto> searchVacancy(String keyword){
        List<Vacancy> vacancies = (keyword == null || keyword.trim().isEmpty())
            ? vacancyRepository.findAll()
            : vacancyRepository.searchByKeyword(keyword.trim());

        return vacancies.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<VacancyResponseDto> getVacanciesByEmployer(Long employerId) {
        return vacancyRepository.findByEmployerIdAndIsActiveTrue(employerId).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public Page<VacancyResponseDto> getVacanciesByCompany(String company, int page, int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1));
        return vacancyRepository.findByCompanyAndIsActiveTrue(company, pageable)
                .map(this::mapToResponseDto);
    }

    @Override
    public List<CompanyResponseDto> getAllCompanies() {
        return vacancyRepository.countActiveVacanciesGroupedByCompany().stream()
                .map(row -> new CompanyResponseDto((String) row[0], (Long) row[1]))
                .toList();
    }
}