package com.example.headhanter.models;



import jdk.jfr.Category;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Slf4j
public class Vacancy {
    public Vacancy(String name, long id, String description,
                   Double salary, Integer expFrom, Integer expTo, Boolean isActive,
                   LocalDateTime createdDate, LocalDateTime updateTime, User author) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.salary = salary;
        this.expFrom = expFrom;
        this.expTo = expTo;
        this.createdDate = createdDate;
        this.updateTime = updateTime;

    }

    private String name;
    private long id;

    private String description;
    private Double salary;

    private Integer expFrom;
    private Integer expTo;
    private Boolean isActive;

    private LocalDateTime createdDate;
    private LocalDateTime updateTime;


    private Category category;

    private User author;


}
