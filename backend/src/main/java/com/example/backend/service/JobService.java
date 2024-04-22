package com.example.backend.service;

import com.example.backend.dto.jobDTO.JobCreateRequestDTO;
import com.example.backend.dto.jobDTO.JobResponseDTO;
import com.example.backend.dto.jobDTO.Jobs;
import com.example.backend.dto.jobDTO.JobUpdateRequestDTO;

public interface JobService {

    Jobs createJob(JobCreateRequestDTO jobRequestDTO);

    JobResponseDTO getAllJobsByUser(String search, String jobStatus, String jobType, String sort, int page, int limit);

    Jobs getJob(String jobId);

    Jobs updateJobById(JobUpdateRequestDTO requestJob, String jobId);

    void deleteJobById(String jobId);

}
