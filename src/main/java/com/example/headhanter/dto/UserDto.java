package com.example.headhanter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Email обязателен для заполнения")
    @Email(message = "Некорректный формат email адреса")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 3, max = 50, message = "Длина пароля должна быть от 3 символов")
    private String password;

    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Некорректный формат телефона (пример: +79001112233)")
    private String phone;

    @NotBlank(message = "Укажите тип аккаунта (APPLICANT или EMPLOYER)")
    private String accountType;
}