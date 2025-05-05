package com.example.scmservice.service;

import com.example.scmservice.model.ScmInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScmServiceTest {

    private ScmService scmService;

    @BeforeEach
    void setUp() {
        scmService = new ScmService();
    }

    @Test
    void getScmInfoByAssetCode_existingCode_returnsScmInfo() {
        // Given an existing asset code
        String existingCode = "APP001";
        
        // When retrieving SCM info
        Optional<ScmInfo> result = scmService.getScmInfoByAssetCode(existingCode);
        
        // Then SCM info should be returned
        assertTrue(result.isPresent());
        assertEquals(existingCode, result.get().getAssetCode());
        assertEquals("Git", result.get().getScmType());
        assertEquals("https://github.com/org/app001", result.get().getRepositoryUrl());
    }

    @Test
    void getScmInfoByAssetCode_nonExistingCode_returnsEmpty() {
        // Given a non-existing asset code
        String nonExistingCode = "NONEXISTENT";
        
        // When retrieving SCM info
        Optional<ScmInfo> result = scmService.getScmInfoByAssetCode(nonExistingCode);
        
        // Then empty optional should be returned
        assertFalse(result.isPresent());
    }

    @Test
    void addScmInfo_newAssetCode_addsAndReturnsScmInfo() {
        // Given a new SCM info
        ScmInfo newInfo = new ScmInfo("NEW001", "Bitbucket", "https://bitbucket.org/org/new001");
        
        // When adding the SCM info
        ScmInfo result = scmService.addScmInfo(newInfo);
        
        // Then the SCM info should be added and returned
        assertEquals(newInfo, result);
        
        // And it should be retrievable
        Optional<ScmInfo> retrieved = scmService.getScmInfoByAssetCode("NEW001");
        assertTrue(retrieved.isPresent());
        assertEquals("Bitbucket", retrieved.get().getScmType());
    }

    @Test
    void addScmInfo_existingAssetCode_updatesAndReturnsScmInfo() {
        // Given an updated SCM info for an existing asset code
        ScmInfo updatedInfo = new ScmInfo("APP001", "GitHub", "https://github.com/neworg/app001");
        
        // When updating the SCM info
        ScmInfo result = scmService.addScmInfo(updatedInfo);
        
        // Then the SCM info should be updated and returned
        assertEquals(updatedInfo, result);
        
        // And the updated info should be retrievable
        Optional<ScmInfo> retrieved = scmService.getScmInfoByAssetCode("APP001");
        assertTrue(retrieved.isPresent());
        assertEquals("GitHub", retrieved.get().getScmType());
        assertEquals("https://github.com/neworg/app001", retrieved.get().getRepositoryUrl());
    }
}