package com.example.headhanter.service.impl;

import com.example.headhanter.dto.request.UserDto;
import com.example.headhanter.models.RoleEntity;
import com.example.headhanter.models.User;
import com.example.headhanter.repository.UserRepository;
import com.example.headhanter.service.FileService;
import com.example.headhanter.service.RoleService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final FileService fileService;

    @Override
    @Transactional
    public User createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Пользователь с email " + userDto.getEmail() + " уже существует");
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .phone(userDto.getPhone())
                .role(resolveRole(userDto.getAccountType()))
                .build();

        return userRepository.save(user);
    }

    private RoleEntity resolveRole(String accountType) {
        String roleName = (accountType != null && !accountType.isBlank()) ? accountType : "APPLICANT";
        return roleService.getByName(roleName);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с id " + id + " не найден"));
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserDto userDto) {
        User user = getUserById(id);

        if (userDto.getEmail() != null && !userDto.getEmail().equalsIgnoreCase(user.getEmail())
                && userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Пользователь с email " + userDto.getEmail() + " уже существует");
        }

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setRole(resolveRole(userDto.getAccountType()));

        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByName(String name) {
        return List.of();
    }

    @Override
    public List<User> getUsersByPhone(String phone) {
        return List.of();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));
    }

    @Override
    public boolean checkUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User uploadAvatar(Long userId, MultipartFile file) {
        User user = getUserById(userId);

        String oldAvatarUrl = user.getAvatarUrl();
        String newFileName = fileService.saveAvatar(file);

        user.setAvatarUrl(newFileName);
        User savedUser = userRepository.save(user);

        if (oldAvatarUrl != null && !oldAvatarUrl.equals(newFileName)) {
            fileService.deleteAvatar(oldAvatarUrl);
        }

        return savedUser;
    }
}