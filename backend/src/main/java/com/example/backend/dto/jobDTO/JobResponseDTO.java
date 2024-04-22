package com.example.backend.dto.jobDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDTO {

    private long totalJobs;
    private int numOfPages;
    private int currentPage;
    private List<Jobs> jobs;
}
