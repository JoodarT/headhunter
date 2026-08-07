package com.example.headhanter.controller;

import com.example.headhanter.dto.request.UserDto;
import com.example.headhanter.service.ResumeService;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ViewController{

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
    public String registerUser(@ModelAttribute UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        model.addAttribute("user", userService.getAllUsers().stream().findFirst().orElse(null));
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showProfileEditPage(Model model) {
        model.addAttribute("user", userService.getAllUsers().stream().findFirst().orElse(null));
        return "profile-edit";
    }

    @GetMapping("/resumes")
    public String showResumesPage(Model model) {
        model.addAttribute("resumes", resumeService.getAllResumes());
        return "resumes";
    }

    @GetMapping("/vacancies")
    public String showVacanciesPage(Model model) {
        model.addAttribute("vacancies", vacancyService.getAll());
        return "vacancies";
    }
}