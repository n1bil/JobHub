package com.example.backend.service;

import com.example.backend.dto.JobRequestDTO;
import com.example.backend.dto.JobResponseDTO;

public interface JobService {

    JobResponseDTO createJob(JobRequestDTO jobRequestDTO);

}
