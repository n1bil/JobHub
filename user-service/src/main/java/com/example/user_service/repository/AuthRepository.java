package com.example.user_service.repository;

import com.example.user_service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthRepository extends MongoRepository<User, String> {

    Boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);

}
