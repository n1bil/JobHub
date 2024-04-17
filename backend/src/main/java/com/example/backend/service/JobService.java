package com.example.backend.service;

import com.example.backend.dto.jobDTO.JobCreateRequestDTO;
import com.example.backend.dto.jobDTO.JobResponseDTO;
import com.example.backend.dto.jobDTO.JobUpdateRequestDTO;

import java.util.List;

public interface JobService {

    JobResponseDTO createJob(JobCreateRequestDTO jobRequestDTO);

    List<JobResponseDTO> getAllJobsByUser();

    JobResponseDTO getJob(String jobId);

    JobResponseDTO updateJobById(JobUpdateRequestDTO requestJob, String jobId);

    void deleteJobById(String jobId);

}
