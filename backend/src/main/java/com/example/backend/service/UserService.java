package com.example.backend.service;

import com.example.backend.dto.UserRequestDTO;
import com.example.backend.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO register(UserRequestDTO userRequest);



}
