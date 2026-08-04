package com.example.headhanter.service.impl;

import com.example.headhanter.dao.UserDao;
import com.example.headhanter.dto.UserDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.FileService;
import com.example.headhanter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Override
    public User createUser(UserDto userDto) {
        if (userDao.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setAccountType(userDto.getAccountType());

        return userDao.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new NoSuchElementException("Пользователь с ID " + id + " не найден");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NoSuchElementException("Пользователь с email " + email + " не найден");
        }
        return user;
    }

    @Override
    public User updateUser(Long id, UserDto userDto) {
        User existingUser = getUserById(id);

        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhone(userDto.getPhone());
        existingUser.setAccountType(userDto.getAccountType());

        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userDao.update(existingUser);
        return existingUser;
    }

    @Override
    public void deleteUser(Long id) {
        getUserById(id);
        userDao.deleteById(id);
    }

    @Override
    public List<User> getUsersByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> getUsersByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    public boolean checkUserExists(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User uploadAvatar(Long userId, MultipartFile file) {
        User user = getUserById(userId);

        if (file != null && !file.isEmpty()) {
            String avatarPath = fileService.saveAvatar(file);
            user.setAvatarFileName(avatarPath);
            userDao.update(user);
        }

        return user;
    }
}