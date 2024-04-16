package com.example.backend.service;

import com.example.backend.dto.AuthDTO.AuthRequestDTO;
import com.example.backend.dto.AuthDTO.AuthResponseDTO;
import com.example.backend.dto.userDTO.UserRequestDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;

public interface UserService {

    UserResponseDTO register(UserRequestDTO userRequest);

    AuthResponseDTO login(AuthRequestDTO loginDto);

}
