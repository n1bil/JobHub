package com.example.apigateway.mapper;

import com.example.apigateway.dto.RegisterRequestDTO;
import com.example.apigateway.entity.User;
import com.example.apigateway.dto.RegisterResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(RegisterRequestDTO userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .location(userRequest.getLocation())
                .role(userRequest.getRole())
                .build();
    }

    public RegisterResponseDTO mapToResponseDTO(User user) {
        return RegisterResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .location(user.getLocation())
                .role(user.getRole())
                .avatar(user.getAvatar())
                .build();
    }
}
