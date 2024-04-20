package com.example.backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public void storeFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        try {
            Files.copy(file.getInputStream(), uploadPath.resolve(file.getOriginalFilename()));
        } catch (IOException ex) {
            throw new IOException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }
}
