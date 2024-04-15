package com.example.backend.mapper;

import com.example.backend.dto.UserRequestDTO;
import com.example.backend.dto.UserResponseDTO;
import com.example.backend.entity.User;

public class UserMapper {

    public User mapToUser(UserRequestDTO userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .location(userRequest.getLocation())
                .role(userRequest.getRole())
                .build();
    }

    public UserResponseDTO mapToResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .location(user.getLocation())
                .build();
    }
}
