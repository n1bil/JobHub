package com.example.job_service.config;

import com.example.job_service.mapper.JobMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JobMapper jobMapper() {
        return new JobMapper();
    }

}
