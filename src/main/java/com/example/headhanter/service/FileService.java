package com.example.headhanter.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveAvatar(MultipartFile file);
    void deleteAvatar(String fileName);
}