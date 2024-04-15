package com.example.backend.service.impl;

import com.example.backend.controller.JobController;
import com.example.backend.dto.JobRequestDTO;
import com.example.backend.dto.JobResponseDTO;
import com.example.backend.entity.Job;
import com.example.backend.exception.NotFoundException;
import com.example.backend.mapper.JobMapper;
import com.example.backend.repository.JobRepository;
import com.example.backend.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

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

        return jobMapper.mapToJobResponseDTO(savedJob);
    }

    @Override
    public List<JobResponseDTO> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(jobMapper::mapToJobResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobResponseDTO getJob(String jobId) {
        return jobRepository.findById(jobId)
                .map(jobMapper::mapToJobResponseDTO)
                .orElseThrow(() -> new NotFoundException("Job with id '" + jobId + "' not found"));
    }
}
