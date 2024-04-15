package com.example.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobUpdateRequestDTO {

    @NotEmpty(message = "Company name is required")
    @Size(min = 3, max = 20, message = "Company name must be between 3 and 20 characters")
    private String company;

    @NotEmpty(message = "Position title is required")
    private String position;

    private String jobStatus;
    private String jobType;

    @NotEmpty(message = "Job location is required")
    private String jobLocation;

}
