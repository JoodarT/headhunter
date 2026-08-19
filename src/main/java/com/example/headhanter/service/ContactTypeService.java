package com.example.headhanter.service;

import com.example.headhanter.models.ContactType;

import java.util.List;

public interface ContactTypeService {
    List<ContactType> getAll();
    ContactType getById(Long id);
}
