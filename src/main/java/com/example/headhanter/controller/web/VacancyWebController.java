package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.CategoryService;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyWebController {

    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllVacancies(Model model) {
        List<VacancyResponseDto> vacancies = vacancyService.getAll();
        model.addAttribute("vacancies", vacancies);
        return "vacancies";
    }

    @GetMapping("/create")
    public String showCreateVacancyPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        model.addAttribute("vacancyDto", new VacancyCreateDto());
        model.addAttribute("categories", categoryService.getAll());
        return "vacancy-create";
    }

    @PostMapping("/create")
    public String createVacancy(
            @Valid @ModelAttribute("vacancyDto") VacancyCreateDto vacancyDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("error", WebValidationUtils.toErrorMessage(bindingResult));
            return "vacancy-create";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        vacancyDto.setEmployerId(currentUser.getId());

        VacancyResponseDto created = vacancyService.create(vacancyDto);
        return "redirect:/vacancies/" + created.getId();
    }

    // Ограничение :\\d+ гарантирует, что сюда попадут только числовые ID
    @GetMapping("/{id:\\d+}")
    public String showVacancyDetail(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        vacancyService.incrementViews(id);

        VacancyResponseDto vacancy = vacancyService.getById(id);
        model.addAttribute("vacancy", vacancy);

        boolean isOwner = false;
        if (userDetails != null) {
            User currentUser = userService.getUserByEmail(userDetails.getUsername());
            if (vacancy.getEmployerId() != null && vacancy.getEmployerId().equals(currentUser.getId())) {
                isOwner = true;
            }
        }
        model.addAttribute("isOwner", isOwner);

        return "vacancy-detail";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String showEditVacancyPage(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        VacancyResponseDto vacancy = vacancyService.getById(id);
        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        if (vacancy.getEmployerId() == null || !vacancy.getEmployerId().equals(currentUser.getId())) {
            return "redirect:/vacancies?error=forbidden";
        }

        VacancyCreateDto dto = new VacancyCreateDto();
        dto.setEmployerId(vacancy.getEmployerId());
        dto.setTitle(vacancy.getTitle());
        dto.setDescription(vacancy.getDescription());
        dto.setSalary(vacancy.getSalary());
        dto.setCategoryId(vacancy.getCategoryId());

        model.addAttribute("vacancyDto", dto);
        model.addAttribute("vacancyId", id);
        model.addAttribute("categories", categoryService.getAll());

        return "vacancy-edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String updateVacancy(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("vacancyDto") VacancyCreateDto vacancyDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());

        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancyId", id);
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("error", WebValidationUtils.toErrorMessage(bindingResult));
            return "vacancy-edit";
        }

        vacancyDto.setEmployerId(currentUser.getId());

        vacancyService.update(id, vacancyDto, currentUser.getId());
        return "redirect:/vacancies/" + id;
    }

    @PostMapping("/{id:\\d+}/delete")
    public String deleteVacancy(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        vacancyService.delete(id, currentUser.getId());

        return "redirect:/resumes";
    }
}