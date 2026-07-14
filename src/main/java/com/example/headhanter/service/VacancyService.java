package com.example.headhanter.service;

import com.example.headhanter.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacancyService {

    private final UserService userService;

    @Autowired
    public VacancyService(UserService userService) {
        this.userService = userService;
    }

    public Vacancy createDemoVacancy() {
        User author = userService.getFakeAuthor();

        return new Vacancy(100L, "Java Разработчик", "Писать чистый код на Spring Boot", author);
    }
}