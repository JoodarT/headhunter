package com.example.headhanter.service.impl;

import com.example.headhanter.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${app.upload.dir:uploads/avatars}")
    private String uploadDir;

    @Override
    public String saveAvatar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл аватара не может быть пустым");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Допускаются только файлы изображений");
        }

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = ".png";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(fileName);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            log.info("Файл успешно сохранен: {}", fileName);
            return fileName;
        } catch (IOException e) {
            log.error("Ошибка при сохранении аватара", e);
            throw new RuntimeException("Не удалось сохранить аватар", e);
        }
    }

    @Override
    public void deleteAvatar(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return;
        }

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName).normalize();

            if (!filePath.startsWith(uploadPath)) {
                log.warn("Попытка удалить файл вне директории аватаров: {}", fileName);
                return;
            }

            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("Не удалось удалить старый аватар {}", fileName, e);
        }
    }
}