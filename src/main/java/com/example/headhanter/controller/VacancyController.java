package com.example.headhanter.controller;

import com.example.headhanter.dto.VacancyCreateDto;
import com.example.headhanter.dto.VacancyResponseDto;
import com.example.headhanter.service.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
@Tag(name = "Вакансии", description = "Управление вакансиями: создание, обновление, удаление и поиск")
@SecurityRequirement(name = "basicAuth")
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    @Operation(summary = "Создать новую вакансию", description = "Доступно только пользователям с ролью EMPLOYER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Вакансия успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в теле запроса (ошибка валидации)"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Отказ в доступе (нужна роль EMPLOYER)")
    })
    public ResponseEntity<VacancyResponseDto> createVacancy(@Valid @RequestBody VacancyCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Получить список всех вакансий", description = "Возвращает полный список доступных вакансий. Доступно для авторизованных пользователей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список вакансий успешно получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    public ResponseEntity<List<VacancyResponseDto>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить вакансию по ID", description = "Возвращает подробную информацию о конкретной вакансии.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вакансия найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "404", description = "Вакансия с указанным ID не найдена")
    })
    public ResponseEntity<VacancyResponseDto> getVacancyById(
            @Parameter(description = "Уникальный идентификатор вакансии", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getById(id));
    }

    @GetMapping("/responded/user/{userId}")
    @Operation(summary = "Получить вакансии, на которые откликнулся пользователь", description = "Возвращает список вакансий, по которым у соискателя есть отклики.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список вакансий получен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<List<VacancyResponseDto>> getRespondedVacanciesByUser(
            @Parameter(description = "Идентификатор соискателя", example = "3")
            @PathVariable Long userId) {
        return ResponseEntity.ok(vacancyService.getRespondedVacanciesByUser(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить вакансию", description = "Редактирование существующей вакансии. Доступно только работодателю (EMPLOYER).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вакансия успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в запросе"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Отказ в доступе"),
            @ApiResponse(responseCode = "404", description = "Вакансия не найдена")
    })
    public ResponseEntity<VacancyResponseDto> updateVacancy(
            @Parameter(description = "ID обновляемой вакансии", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody VacancyCreateDto dto) {
        return ResponseEntity.ok(vacancyService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить вакансию", description = "Удаляет вакансию по её ID. Доступно только работодателю (EMPLOYER).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Вакансия успешно удалена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Отказ в доступе"),
            @ApiResponse(responseCode = "404", description = "Вакансия не найдена")
    })
    public ResponseEntity<Void> deleteVacancy(
            @Parameter(description = "ID удаляемой вакансии", example = "1")
            @PathVariable Long id) {
        vacancyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}