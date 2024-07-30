package com.example.job_service;

import com.example.job_service.dto.jobDTO.JobCreateRequestDTO;
import com.example.job_service.repository.JobRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class JobServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobRepository jobRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateJob() throws Exception {
        JobCreateRequestDTO jobRequest = getJobRequest();

        String jobRequestString = objectMapper.writeValueAsString(jobRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobRequestString))
                .andExpect(status().isCreated());

        assertEquals(1, jobRepository.findAll().size());
    }

    private JobCreateRequestDTO getJobRequest() {
        return JobCreateRequestDTO.builder()
                .company("Google")
                .position("Frontend")
                .jobStatus("Pending")
                .jobType("Full-time")
                .jobLocation("San-Francisco")
                .build();
    }

}
