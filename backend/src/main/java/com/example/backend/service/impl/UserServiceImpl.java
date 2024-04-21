package com.example.backend.service.impl;

import com.example.backend.controller.JobController;
import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.StatsResponseDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;
import com.example.backend.entity.Job;
import com.example.backend.entity.User;
import com.example.backend.exception.NotFoundException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.AuthRepository;
import com.example.backend.repository.JobRepository;
import com.example.backend.service.UserService;
import com.example.backend.utils.CloudinaryService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private AuthRepository authRepository;
    private UserMapper userMapper;
    private MongoTemplate mongoTemplate;
    private CloudinaryService cloudinaryService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(AuthRepository authRepository, UserMapper userMapper, MongoTemplate mongoTemplate, CloudinaryService cloudinaryService, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userMapper = userMapper;
        this.mongoTemplate = mongoTemplate;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
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

    @Override
    public StatsResponseDTO showStats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = authRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email " + email + " not found"));

        Aggregation aggregation = newAggregation(
                lookup("users", "createdBy.$id", "_id", "user"),
                match(Criteria.where("user._id").is(new ObjectId(user.getId()))),
                group("jobStatus").count().as("count")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "jobs", Map.class);
        List<Map> mappedResults = results.getMappedResults();

        Map<String, Integer> formattedResults = new HashMap<>();
        for (Map map : mappedResults) {
            String status = (String) map.get("_id");
            Integer count = ((Number) map.get("count")).intValue();
            formattedResults.put(status, count);
        }

        String[] possibleStatuses = {"pending", "interview", "declined"};
        for (String status : possibleStatuses) {
            formattedResults.putIfAbsent(status, 0);
        }

        StatsResponseDTO responseDTO = new StatsResponseDTO();
        responseDTO.setDefaultStats(formattedResults);

        return responseDTO;
    }


}
