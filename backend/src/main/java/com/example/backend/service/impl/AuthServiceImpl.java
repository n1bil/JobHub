package com.example.backend.service.impl;

import com.example.backend.dto.AuthDTO.AuthRequestDTO;
import com.example.backend.dto.AuthDTO.AuthResponseDTO;
import com.example.backend.dto.userDTO.UserRequestDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.AuthRepository;
import com.example.backend.security.JwtTokenProvider;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${admin.email}")
    private String email;
    private AuthRepository authRepository;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthRepository userRepository, UserMapper userMapper,
                           AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO userRequest) {
        if (authRepository.existsUserByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = userMapper.mapToUser(userRequest);

        if ((userRequest.getEmail()).equals(email)) {
            user.setRole("admin");
        }

        authRepository.save(user);

        return userMapper.mapToResponseDTO(user);
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()
        ));

        User foundUser = authRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email));
        String token = jwtTokenProvider.generateToken(foundUser);

        AuthResponseDTO jwtAuthResponse = new AuthResponseDTO();
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }

}















