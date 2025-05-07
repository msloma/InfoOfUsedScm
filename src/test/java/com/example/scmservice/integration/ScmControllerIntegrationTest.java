package com.example.scmservice.integration;

import com.example.scmservice.config.TestRedisConfiguration;
import com.example.scmservice.model.ScmInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestRedisConfiguration.class)
@TestPropertySource(properties = {
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379"
})
public class ScmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetExistingScmInfo() throws Exception {
        // The service initializes with sample data including APP001
        mockMvc.perform(get("/api/scm/APP001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.assetCode", is("APP001")))
                .andExpect(jsonPath("$.scmType", is("Git")))
                .andExpect(jsonPath("$.repositoryUrl", is("https://github.com/org/app001")));
    }

    @Test
    void testGetNonExistentScmInfo() throws Exception {
        mockMvc.perform(get("/api/scm/NONEXISTENT"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("Asset not found with assetCode: 'NONEXISTENT'")))
                .andExpect(jsonPath("$.path").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testAddNewScmInfo() throws Exception {
        ScmInfo newScmInfo = new ScmInfo("API001", "Bitbucket", "https://bitbucket.org/org/api001");

        mockMvc.perform(post("/api/scm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newScmInfo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.assetCode", is("API001")))
                .andExpect(jsonPath("$.scmType", is("Bitbucket")))
                .andExpect(jsonPath("$.repositoryUrl", is("https://bitbucket.org/org/api001")));

        // Verify we can retrieve it
        mockMvc.perform(get("/api/scm/API001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetCode", is("API001")));
    }

    @Test
    void testUpdateExistingScmInfo() throws Exception {
        // First add a new entry
        ScmInfo originalInfo = new ScmInfo("UPDATE_API", "Git", "https://github.com/org/update-api");

        mockMvc.perform(post("/api/scm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(originalInfo)))
                .andExpect(status().isOk());

        // Then update it
        ScmInfo updatedInfo = new ScmInfo("UPDATE_API", "GitLab", "https://gitlab.com/org/update-api");

        mockMvc.perform(post("/api/scm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedInfo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scmType", is("GitLab")))
                .andExpect(jsonPath("$.repositoryUrl", is("https://gitlab.com/org/update-api")));

        // Verify the update was successful
        mockMvc.perform(get("/api/scm/UPDATE_API"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scmType", is("GitLab")));
    }
}
