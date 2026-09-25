package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.VacancyCreateDto;
import com.example.headhanter.dto.response.ResumeResponseDto;
import com.example.headhanter.dto.response.VacancyResponseDto;
import com.example.headhanter.dto.response.VacancySearchResultDto;
import com.example.headhanter.models.User;
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
            @RequestParam(value = "category", required = false) Long category,
            Model model
    ) {
        Page<VacancyResponseDto> vacancyPage = fetchVacancies(page, sort, direction, search, company, category);

        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("search", search.trim());
        } else if (company != null && !company.trim().isEmpty()) {
            model.addAttribute("company", company.trim());
        }

        model.addAttribute("vacancies", vacancyPage.getContent());
        model.addAttribute("totalPages", vacancyPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("selectedCategory", category);

        return "vacancies/vacancies";
    }

    @GetMapping("/data")
    @ResponseBody
    public VacancySearchResultDto getVacanciesData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "company", required = false) String company,
            @RequestParam(value = "category", required = false) Long category
    ) {
        Page<VacancyResponseDto> vacancyPage = fetchVacancies(page, sort, direction, search, company, category);

        VacancySearchResultDto result = new VacancySearchResultDto();
        result.setVacancies(vacancyPage.getContent());
        result.setCurrentPage(page);
        result.setTotalPages(vacancyPage.getTotalPages());
        return result;
    }

    private Page<VacancyResponseDto> fetchVacancies(int page, String sort, String direction, String search, String company, Long category) {
        boolean ascending = "asc".equalsIgnoreCase(direction);

        if (search != null && !search.trim().isEmpty()) {
            return vacancyService.searchVacancy(search.trim(), page, PAGE_SIZE);
        }
        if (company != null && !company.trim().isEmpty()) {
            return vacancyService.getVacanciesByCompany(company.trim(), page, PAGE_SIZE);
        }
        return vacancyService.getAllPaged(page, PAGE_SIZE, sort, ascending, category);
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
        dto.setExperienceFrom(vacancy.getExperienceFrom());
        dto.setExperienceTo(vacancy.getExperienceTo());
        dto.setCategoryId(vacancy.getCategoryId());
        dto.setIsActive(vacancy.getIsActive());

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