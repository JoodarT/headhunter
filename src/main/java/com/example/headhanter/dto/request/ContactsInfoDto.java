package com.example.headhanter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactsInfoDto {
    @NotBlank(message = "Укажите номер телефона")
    private String phone;

    @NotBlank(message = "Укажите email")
    @Email(message = "Некорректный email")
    private String email;

    private String telegram;
    private String linkedin;
}