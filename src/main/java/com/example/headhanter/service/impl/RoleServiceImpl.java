package com.example.headhanter.service.impl;

import com.example.headhanter.models.RoleEntity;
import com.example.headhanter.repository.RoleRepository;
import com.example.headhanter.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity getByName(String role) {
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new NoSuchElementException("Роль " + role + " не настроена в таблице roles"));
    }
}
