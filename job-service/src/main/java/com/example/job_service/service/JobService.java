package com.example.job_service.service;

import com.example.job_service.dto.UsersJobsCountResponse;
import com.example.job_service.dto.jobDTO.JobCreateRequestDTO;
import com.example.job_service.dto.jobDTO.JobUpdateRequestDTO;
import com.example.job_service.dto.jobDTO.JobsResponseDTO;
import com.example.job_service.dto.jobDTO.JobResponseDTO;
import reactor.core.publisher.Mono;

public interface JobService {

    JobResponseDTO createJob(JobCreateRequestDTO jobRequestDTO);

    JobsResponseDTO getAllJobsByUser(String search, String jobStatus, String jobType, String sort, int page, int limit);

    JobResponseDTO getJob(String jobId);

    JobResponseDTO updateJobById(JobUpdateRequestDTO requestJob, String jobId);

    void deleteJobById(String jobId);

    UsersJobsCountResponse getApplicationStats();

}
