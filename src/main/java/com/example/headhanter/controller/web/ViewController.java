package com.example.headhanter.controller.web;

import com.example.headhanter.dto.request.UserDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/resumes";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDto userDto, Model model) {
        try {
            userService.createUser(userDto);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage() != null ? e.getMessage() : "Ошибка при регистрации");
            model.addAttribute("userDto", userDto);
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        if (registered != null) {
            model.addAttribute("registered", true);
        }
        return "login";
    }

    @GetMapping("/profile")
    public String showProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showProfileEditPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile-edit";
    }

//    @GetMapping("/resumes")
//    public String showResumesPage(Model model) {
//        model.addAttribute("resumes", resumeService.getAllResumes());
//        return "resumes";
//    }

    @GetMapping("/vacancies")
    public String showVacanciesPage(Model model) {
        model.addAttribute("vacancies", vacancyService.getAll());
        return "vacancies";
    }
}