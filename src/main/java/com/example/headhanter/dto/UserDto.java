package com.example.headhanter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @NotBlank(message = "Имя пользователя обязательное поле")
    String name;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Email должен быть корректным адресом электронной почты")
    String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 4, max = 24, message = "Пароль должен быть от 4 до 24 символов")
    String password;

    @NotBlank(message = "Телефон обязателен")
    String phone;

    @NotBlank(message = "Укажите тип аккаунта (APPLICANT или EMPLOYER)")
    String accountType;
}