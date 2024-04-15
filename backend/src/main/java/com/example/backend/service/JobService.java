package com.example.backend.service;

import com.example.backend.dto.JobCreateRequestDTO;
import com.example.backend.dto.JobResponseDTO;
import com.example.backend.dto.JobUpdateRequestDTO;

import java.util.List;

public interface JobService {

    JobResponseDTO createJob(JobCreateRequestDTO jobRequestDTO);

    List<JobResponseDTO> getAllJobs();

    JobResponseDTO getJob(String jobId);

    JobResponseDTO updateJobById(JobUpdateRequestDTO requestJob, String jobId);

    void deleteJobById(String jobId);

}
