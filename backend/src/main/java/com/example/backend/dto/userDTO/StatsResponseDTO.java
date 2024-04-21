package com.example.backend.dto.userDTO;

import lombok.*;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponseDTO {

    private Map<String, Integer> defaultStats;

    public Map<String, Integer> getDefaultStats() {
        return defaultStats;
    }

    public void setDefaultStats(Map<String, Integer> defaultStats) {
        this.defaultStats = defaultStats;
    }
}

