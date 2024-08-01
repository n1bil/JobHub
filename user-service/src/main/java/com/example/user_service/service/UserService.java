package com.example.user_service.service;

import com.example.user_service.dto.UsersJobsResponse;
import com.example.user_service.dto.userDTO.StatsResponseDTO;
import com.example.user_service.dto.userDTO.UserResponseDTO;
import com.example.user_service.dto.userDTO.UserUpdateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

//    UserResponseDTO getCurrentUser();
//
//    UserResponseDTO updateUser(MultipartFile avatar, UserUpdateRequestDTO userUpdateRequest);

    UsersJobsResponse getApplicationUserStats();

//    StatsResponseDTO showStats();
}
