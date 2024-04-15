package com.example.backend.service.impl;

import com.example.backend.controller.JobController;
import com.example.backend.dto.JobRequestDTO;
import com.example.backend.dto.JobResponseDTO;
import com.example.backend.entity.Job;
import com.example.backend.mapper.JobMapper;
import com.example.backend.repository.JobRepository;
import com.example.backend.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private JobRepository jobRepository;
    private JobMapper jobMapper;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public JobResponseDTO createJob(JobRequestDTO jobRequestDTO) {
        Job job = jobMapper.mapToJob(jobRequestDTO);

        Job savedJob = jobRepository.save(job);
        logger.info("Job added: {}", savedJob);
        return jobMapper.mapToJobResponseDTO(savedJob);
    }
}
