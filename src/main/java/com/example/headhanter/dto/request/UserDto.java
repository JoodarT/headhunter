package com.example.headhanter.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    public interface OnCreate {}

    @NotBlank(message = "{validation.user.email.notBlank}")
    @Email(message = "{validation.user.email.invalid}")
    private String email;

    @NotBlank(message = "{validation.user.password.notBlank}", groups = OnCreate.class)
    @Size(min = 6, message = "{validation.user.password.size}")
    private String password;

    @NotBlank(message = "{validation.user.name.notBlank}")
    private String name;

    @NotBlank(message = "{validation.user.surname.notBlank}")
    private String surname;

    @NotNull(message = "{validation.user.age.notNull}")
    @Min(value = 16, message = "{validation.user.age.min}")
    @Max(value = 100, message = "{validation.user.age.max}")
    private Integer age;

    @NotBlank(message = "{validation.user.phone.notBlank}")
    private String phone;

    @NotNull(message = "{validation.user.accountType.notNull}")
    private String accountType;

}