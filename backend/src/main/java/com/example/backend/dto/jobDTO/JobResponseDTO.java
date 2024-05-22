package com.example.backend.dto.jobDTO;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDTO implements Serializable {

    private long totalJobs;
    private int numOfPages;
    private int currentPage;
    private List<Jobs> jobs;
}
