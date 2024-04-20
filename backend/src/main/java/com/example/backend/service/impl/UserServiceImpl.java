package com.example.backend.service.impl;

import com.example.backend.controller.JobController;
import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;
import com.example.backend.entity.Job;
import com.example.backend.entity.User;
import com.example.backend.exception.NotFoundException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.AuthRepository;
import com.example.backend.service.UserService;
import com.example.backend.utils.CloudinaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private AuthRepository authRepository;
    private UserMapper userMapper;
    private MongoTemplate mongoTemplate;
    private CloudinaryService cloudinaryService;

    @Autowired
    public UserServiceImpl(AuthRepository authRepository, UserMapper userMapper, MongoTemplate mongoTemplate, CloudinaryService cloudinaryService) {
        this.authRepository = authRepository;
        this.userMapper = userMapper;
        this.mongoTemplate = mongoTemplate;
        this.cloudinaryService = cloudinaryService;
    }

    public UserResponseDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = authRepository.findByEmail(email).orElseThrow(() ->
                                        new NotFoundException("User with email " + email + " not found"));

        return userMapper.mapToResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(MultipartFile avatar, UserUpdateRequestDTO userUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = authRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email " + email + " not found"));

        user.setName(userUpdateRequest.getName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setPassword(userUpdateRequest.getPassword());
        user.setLocation(userUpdateRequest.getLocation());

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map<?, ?> result = cloudinaryService.upload(avatar);
                String avatarUrl = (String) result.get("secure_url");
                String publicId = (String) result.get("public_id");
                user.setAvatar(avatarUrl);
                user.setAvatarPublicId(publicId);
            } catch (IOException e) {
                String errorMessage = "An error occurred while uploading avatar for user with email: " + email;
                logger.error(errorMessage, e);
                throw new RuntimeException(errorMessage);
            }
        }

        authRepository.save(user);

        return userMapper.mapToResponseDTO(user);
    }

    @Override
    public UsersJobsResponse getApplicationStats() {
        long userCount = mongoTemplate.count(new Query(), User.class);
        long jobCount = mongoTemplate.count(new Query(), Job.class);

        UsersJobsResponse usersJobsResponse = new UsersJobsResponse();
        usersJobsResponse.setUsers(userCount);
        usersJobsResponse.setJobs(jobCount);

        return usersJobsResponse;
    }

}
