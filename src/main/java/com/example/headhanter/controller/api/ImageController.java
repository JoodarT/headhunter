package com.example.headhanter.controller.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images/avatars")
public class ImageController {

    @Value("${app.upload.dir:uploads/avatars}")
    private String uploadDir;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String filename) throws MalformedURLException {
        Path path = Paths.get(uploadDir).resolve(filename);
        Resource resource = new UrlResource(path.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}