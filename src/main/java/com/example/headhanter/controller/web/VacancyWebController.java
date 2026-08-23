package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.models.User;
import com.example.headhanter.repository.VacancyRepository;
import com.example.headhanter.service.CategoryService;
import com.example.headhanter.service.ResponseService;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    private static final int PAGE_SIZE = 6;

    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ResumeService resumeService;
    private final ResponseService responseService;

    @GetMapping
    public String getAllVacancies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "company", required = false) String company,
            Model model
    ) {
        boolean sortByResponses = "responses".equals(sort);
        boolean ascending = "asc".equalsIgnoreCase(direction);

        Page<VacancyResponseDto> vacancyPage = vacancyService.getAllPaged(page, PAGE_SIZE, sortByResponses, ascending);

        model.addAttribute("vacancies", vacancyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", vacancyPage.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
//        model.addAttribute("vacancies", vacancyService);

        if (search != null && !search.trim().isEmpty()){

            model.addAttribute("vacancies", vacancyService.searchVacancy(search));
        } else if (company != null && !company.trim().isEmpty()) {
            model.addAttribute("vacancies", vacancyService.getVacanciesByCompany(company.trim()));
            model.addAttribute("company", company.trim());
        }
        return "vacancies/vacancies";
    }

    @GetMapping("/create")
    public String showCreateVacancyPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        model.addAttribute("vacancyDto", new VacancyCreateDto());
        model.addAttribute("categories", categoryService.getAll());
        return "vacancies/vacancy-create";
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
            return "vacancies/vacancy-create";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        vacancyDto.setEmployerId(currentUser.getId());

        VacancyResponseDto created = vacancyService.create(vacancyDto);
        return "redirect:/vacancies/" + created.getId();
    }

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

            boolean isApplicant = currentUser.getRole() != null && "APPLICANT".equals(currentUser.getRole().getRole());
            if (isApplicant) {
                List<ResumeResponseDto> myResumes = resumeService.getResumesByUserId(currentUser.getId());
                List<ResumeResponseDto> availableResumes = myResumes.stream()
                        .filter(resume -> !responseService.hasResponded(id, resume.getId()))
                        .toList();

                model.addAttribute("isApplicant", true);
                model.addAttribute("myResumes", myResumes);
                model.addAttribute("availableResumes", availableResumes);
            }
        }
        model.addAttribute("isOwner", isOwner);

        return "vacancies/vacancy-detail";
    }

    @PostMapping("/{id:\\d+}/respond")
    public String respondToVacancy(
            @PathVariable("id") Long id,
            @RequestParam Long resumeId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByEmail(userDetails.getUsername());
        responseService.respondToVacancy(resumeId, id, currentUser.getId());

        return "redirect:/vacancies/" + id;
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
        dto.setCompany(vacancy.getCompany());
        dto.setDescription(vacancy.getDescription());
        dto.setSalary(vacancy.getSalary());
        dto.setCategoryId(vacancy.getCategoryId());

        model.addAttribute("vacancyDto", dto);
        model.addAttribute("vacancyId", id);
        model.addAttribute("categories", categoryService.getAll());

        return "vacancies/vacancy-edit";
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
            return "vacancies/vacancy-edit";
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