package com.example.backend.service.impl;

import com.example.backend.dto.jobDTO.JobCreateRequestDTO;
import com.example.backend.dto.jobDTO.JobResponseDTO;
import com.example.backend.dto.jobDTO.JobUpdateRequestDTO;
import com.example.backend.entity.Job;
import com.example.backend.exception.NotFoundException;
import com.example.backend.mapper.JobMapper;
import com.example.backend.repository.JobRepository;
import com.example.backend.service.JobService;
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
    public JobResponseDTO createJob(JobCreateRequestDTO jobRequestDTO) {
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

    @Override
    public JobResponseDTO updateJobById(JobUpdateRequestDTO requestJob, String jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job with id '" + jobId + "' not found"));

        job.setCompany(requestJob.getCompany());
        job.setPosition(requestJob.getPosition());
        job.setJobStatus(requestJob.getJobStatus());
        job.setJobType(requestJob.getJobType());
        job.setPosition(requestJob.getPosition());

        Job updatedJob = jobRepository.save(job);

        return jobMapper.mapToJobResponseDTO(updatedJob);
    }

    @Override
    public void deleteJobById(String jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job with id '" + jobId + "' not found"));

        jobRepository.delete(job);
    }
}
