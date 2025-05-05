package com.example.scmservice.integration;

import com.example.scmservice.model.ScmInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScmServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getScmInfo_existingAssetCode_returnsScmInfo() {
        // When
        ResponseEntity<ScmInfo> response = restTemplate.getForEntity("/api/scm/APP001", ScmInfo.class);
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("APP001", response.getBody().getAssetCode());
        assertEquals("Git", response.getBody().getScmType());
    }

    @Test
    void getScmInfo_nonExistingAssetCode_returnsNotFound() {
        // When
        ResponseEntity<ScmInfo> response = restTemplate.getForEntity("/api/scm/NONEXISTENT", ScmInfo.class);
        
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addScmInfo_validScmInfo_returnsCreatedScmInfo() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        
        ScmInfo newScmInfo = new ScmInfo("TEST003", "Bitbucket", "https://bitbucket.org/test/repo");
        HttpEntity<ScmInfo> request = new HttpEntity<>(newScmInfo, headers);
        
        // When
        ResponseEntity<ScmInfo> response = restTemplate.postForEntity("/api/scm", request, ScmInfo.class);
        
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TEST003", response.getBody().getAssetCode());
        assertEquals("Bitbucket", response.getBody().getScmType());
        
        // Verify it was actually added
        ResponseEntity<ScmInfo> getResponse = restTemplate.getForEntity("/api/scm/TEST003", ScmInfo.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }
}