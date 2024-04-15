package com.example.backend.service;

import com.example.backend.dto.JobRequestDTO;
import com.example.backend.dto.JobResponseDTO;

import java.util.List;

public interface JobService {

    JobResponseDTO createJob(JobRequestDTO jobRequestDTO);

    List<JobResponseDTO> getAllJobs();

    JobResponseDTO getJob(String jobId);

}
