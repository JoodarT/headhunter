package com.example.headhanter.service.impl;

import com.example.headhanter.models.ContactType;
import com.example.headhanter.repository.ContactTypeRepository;
import com.example.headhanter.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactTypeServiceImpl implements ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;

    @Override
    public List<ContactType> getAll() {
        return contactTypeRepository.findAll();
    }

    @Override
    public ContactType getById(Long id) {
        return contactTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Тип контакта с id " + id + " не найден"));
    }
}
