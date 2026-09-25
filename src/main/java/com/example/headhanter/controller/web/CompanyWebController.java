package com.example.headhanter.controller.web;

import com.example.headhanter.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyWebController {

    private final VacancyService vacancyService;

    @GetMapping
    public String getAllCompanies(Model model) {
        model.addAttribute("companies", vacancyService.getAllCompanies());
        return "companies";
    }
}
