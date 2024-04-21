package com.example.backend.service;

import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.StatsResponseDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserResponseDTO getCurrentUser();

    UserResponseDTO updateUser(MultipartFile avatar, UserUpdateRequestDTO userUpdateRequest);

    UsersJobsResponse getApplicationStats();

    StatsResponseDTO showStats();
}
