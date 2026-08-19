package com.example.headhanter.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactInfoDto {

    @NotNull(message = "Укажите тип контакта")
    private Long contactTypeId;

    private String typeName;

    private String value;
}
