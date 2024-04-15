package com.example.backend.repository;

import com.example.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsUserByEmail(String email);

}
