package com.example.headhanter.service.impl;

import com.example.headhanter.models.RoleEntity;
import com.example.headhanter.models.User;
import com.example.headhanter.service.RoleService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Пользователь с email " + email + " не найден");
        }

        RoleEntity role = user.getRole() != null ? user.getRole() : roleService.getByName("APPLICANT");
        List<SimpleGrantedAuthority> authorities = role.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                .toList();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}