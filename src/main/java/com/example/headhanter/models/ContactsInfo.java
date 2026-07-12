package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class ContactsInfo {
    private Long id;
    private ContactType type;
    private String value;
}