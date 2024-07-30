package com.example.job_service;

import com.example.job_service.dto.jobDTO.JobCreateRequestDTO;
import com.example.job_service.entity.Job;
import com.example.job_service.repository.JobRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    void setUp() {
        jobRepository.deleteAll();
    }

    @Test
    void shouldCreateJob() throws Exception {
        JobCreateRequestDTO jobRequest = getJob();

        String jobRequestString = objectMapper.writeValueAsString(jobRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jobRequestString))
                .andExpect(status().isCreated());

        assertEquals(1, jobRepository.findAll().size());
    }

    @Test
    void shouldRetrieveAllJobs() throws Exception {
        saveJob("Google", "Frontend", "Full-time", "San-Francisco");
        saveJob("Microsoft", "Backend", "Part-time", "San-Antonio");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/jobs"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobs.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobs[0].company").value("Google"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobs[0].position").value("Frontend"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobs[1].company").value("Microsoft"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jobs[1].position").value("Backend"));
    }

    private JobCreateRequestDTO getJob() {
        return JobCreateRequestDTO.builder()
                .company("Google")
                .position("Frontend")
                .jobStatus("Pending")
                .jobType("Full-time")
                .jobLocation("San-Francisco")
                .build();
    }

    private void saveJob(String company, String position, String jobType, String jobLocation) {
        Job job = Job.builder()
                .company(company)
                .position(position)
                .jobStatus("Pending")
                .jobType(jobType)
                .jobLocation(jobLocation)
                .build();
        jobRepository.save(job);
    }

}
