package com.example.user_service.dto.userDTO;

import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatsResponseDTO {

    private Map<String, Integer> defaultStats;
    private List<Map> monthlyApplications;

}

