package com.example.apigateway.service;

import com.example.apigateway.dto.AuthRequestDTO;
import com.example.apigateway.dto.AuthResponseDTO;
import com.example.apigateway.dto.RegisterRequestDTO;
import com.example.apigateway.dto.RegisterResponseDTO;

public interface AuthService {

    RegisterResponseDTO registerUser(RegisterRequestDTO userRequestDTO);

    AuthResponseDTO loginUser(AuthRequestDTO loginDto);

    RegisterResponseDTO getCurrentUser();
}
