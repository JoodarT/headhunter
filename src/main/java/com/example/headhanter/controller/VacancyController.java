package com.example.headhanter.controller;

import com.example.headhanter.models.Vacancy;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.VacancyService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/vacancy")

public class VacancyController {

    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService){
        this.vacancyService = vacancyService;
    }

    @GetMapping("/demo")
    public Vacancy getDemoVacancy() {
        return vacancyService.createDemoVacancy();
    }
}
