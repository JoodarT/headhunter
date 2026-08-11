package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyWebController {

    private final VacancyService vacancyService;
    private final UserService userService;

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
        return "vacancy-create";
    }

    @PostMapping("/create")
    public String createVacancy(
            @ModelAttribute("vacancyDto") VacancyCreateDto vacancyDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        vacancyDto.setEmployerId(currentUser.getId());

        vacancyService.create(vacancyDto);
        return "redirect:/vacancies";
    }

    @GetMapping("/{id}")
    public String showVacancyDetail(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ) {
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

    @GetMapping("/{id}/edit")
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
        dto.setCategoryId(vacancy.getCategory());

        model.addAttribute("vacancyDto", dto);
        model.addAttribute("vacancyId", id);

        return "vacancy-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateVacancy(
            @PathVariable("id") Long id,
            @ModelAttribute("vacancyDto") VacancyCreateDto vacancyDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        vacancyDto.setEmployerId(currentUser.getId());

        vacancyService.update(id, vacancyDto, currentUser.getId());
        return "redirect:/vacancies/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteVacancy(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        vacancyService.delete(id, currentUser.getId());

        return "redirect:/vacancies";
    }
}