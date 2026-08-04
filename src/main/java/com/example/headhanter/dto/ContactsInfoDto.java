package com.example.headhanter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactsInfoDto {

    @NotBlank(message = "Телефон обязателен")
    private String phone;

    @Email(message = "Некорректный email")
    private String email;

    private String telegram;
    private String linkedin;
}