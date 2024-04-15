package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDTO {

    @NotEmpty(message = "Company must be filled")
    private String company;
    @NotEmpty(message = "Position must be filled")
    private String position;
    private String jobStatus = "pending";
    private String jobType = "full-time";
    private String jobLocation = "my-city";

}
