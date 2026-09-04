package com.example.headhanter.service;

import com.example.headhanter.dto.request.UserDto;
import com.example.headhanter.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User createUser(UserDto userDto);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    List<User> getUsersByName(String name);
    List<User> getUsersByPhone(String phone);
    User getUserByEmail(String email);
    boolean checkUserExists(String email);
    User uploadAvatar(Long userId, MultipartFile file);
    String createPasswordResetToken(String email);
    void resetPassword(String token, String newPassword);
    boolean isResetTokenValid(String token);
    void updateLocale(String email, String locale);
}