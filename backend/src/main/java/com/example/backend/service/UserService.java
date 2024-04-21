package com.example.backend.service;

import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.StatsResponseDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserResponseDTO getCurrentUser();

    UserResponseDTO updateUser(MultipartFile avatar, UserUpdateRequestDTO userUpdateRequest);

    UsersJobsResponse getApplicationStats();

    StatsResponseDTO showStats();
}
