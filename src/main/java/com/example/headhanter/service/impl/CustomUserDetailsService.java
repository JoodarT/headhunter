package com.example.headhanter.service.impl;

import com.example.headhanter.models.Role;
import com.example.headhanter.models.RoleEntity;
import com.example.headhanter.models.User;
import com.example.headhanter.repository.RoleRepository;
import com.example.headhanter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с email " + email + " не найден"));

        RoleEntity role = user.getRole();
        if (role == null) {
            role = roleRepository.findByRole(Role.APPLICANT.name())
                    .orElseThrow(() -> new IllegalStateException("Роль APPLICANT не настроена в таблице roles"));
        }

        String authority = role.getAuthority().getAuthority();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(authority)))
                .build();
    }
}