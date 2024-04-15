package com.example.backend.service.impl;

import com.example.backend.dto.UserRequestDTO;
import com.example.backend.dto.UserResponseDTO;
import com.example.backend.entity.User;
import com.example.backend.exception.APIException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Value("${admin.email}")
    private String email;
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO userRequest) {
        if (userRepository.existsUserByEmail(userRequest.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists");
        }

        User user = userMapper.mapToUser(userRequest);

        if ((userRequest.getEmail()).equals(email)) {
            user.setRole("admin");
        }

        userRepository.save(user);

        return userMapper.mapToResponseDTO(user);
    }
}















