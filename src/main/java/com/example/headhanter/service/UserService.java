package com.example.headhanter.service;

import com.example.headhanter.dao.UserDao;
import com.example.headhanter.dto.UserDto;
import com.example.headhanter.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final FileService fileService;

    public List<User> getUsersByName(String name) {
        return userDao.findByName(name);
    }

    public List<User> getUsersByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    public User getUserByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NoSuchElementException("Пользователь с email " + email + " не найден");
        }
        return user;
    }

    public boolean checkUserExists(String email) {
        return userDao.existsByEmail(email);
    }

    public User createUser(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setAccountType(dto.getAccountType());

        return userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUserById(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new NoSuchElementException("Пользователь с id " + id + " не найден");
        }
        return user;
    }

    public User updateUser(Long id, UserDto dto) {
        User existingUser = getUserById(id);

        existingUser.setName(dto.getName());
        existingUser.setEmail(dto.getEmail());
        existingUser.setPassword(dto.getPassword());
        existingUser.setPhone(dto.getPhone());
        existingUser.setAccountType(dto.getAccountType());

        userDao.update(existingUser);
        return existingUser;
    }

    public void deleteUser(Long id) {
        getUserById(id);
        userDao.deleteById(id);
    }

    public User uploadAvatar(Long userId, MultipartFile file) {
        User user = getUserById(userId);
        String avatarFileName = fileService.saveAvatar(file);

        user.setAvatarFileName(avatarFileName);
        userDao.update(user);

        return user;
    }
}