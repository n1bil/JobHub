package com.example.backend.config;

import com.example.backend.mapper.JobMapper;
import com.example.backend.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public JobMapper jobMapper() {
        return new JobMapper();
    }

    @Bean
    public UserMapper userMapper(PasswordEncoder passwordEncoder) {
        return new UserMapper(passwordEncoder);
    }
}
