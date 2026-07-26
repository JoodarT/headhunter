package com.example.headhanter.dto;

import lombok.Data;

@Data
public class ContactsInfoDto {
    private String type; // e.g. "PHONE", "EMAIL", "TELEGRAM"
    private String value;
}