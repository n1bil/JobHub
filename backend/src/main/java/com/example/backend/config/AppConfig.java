package com.example.backend.config;

import com.example.backend.mapper.JobMapper;
import com.example.backend.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JobMapper jobMapper() {
        return new JobMapper();
    }

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}
