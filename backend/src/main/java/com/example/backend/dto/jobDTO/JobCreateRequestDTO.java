package com.example.backend.dto.jobDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCreateRequestDTO {

    @NotEmpty(message = "Company name is required")
    @Size(min = 3, max = 20, message = "Company name must be between 3 and 20 characters")
    private String company;

    @NotEmpty(message = "Position title is required")
    private String position;

    private String jobStatus = "pending";
    private String jobType = "full-time";

    @NotEmpty(message = "Job location is required")
    private String jobLocation = "my-city";

}
