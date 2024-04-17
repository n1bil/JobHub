package com.example.backend.service;

import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;

public interface UserService {

    UserResponseDTO getCurrentUser();

    UserResponseDTO updateUser(UserUpdateRequestDTO userUpdateRequest);

    UsersJobsResponse getApplicationStats();
}
