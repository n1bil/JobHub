package com.example.backend.dto;

import jakarta.validation.constraints.NotEmpty;
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
    private String company;
    @NotEmpty(message = "Position title is required")
    private String position;
    private String jobStatus;
    private String jobType;
    private String jobLocation;

}
