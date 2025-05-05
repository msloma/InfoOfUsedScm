package com.example.scmservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScmInfoTest {

    @Test
    void constructor_noArgs_createsEmptyScmInfo() {
        // When
        ScmInfo scmInfo = new ScmInfo();
        
        // Then
        assertNull(scmInfo.getAssetCode());
        assertNull(scmInfo.getScmType());
        assertNull(scmInfo.getRepositoryUrl());
    }

    @Test
    void constructor_withArgs_createsPopulatedScmInfo() {
        // When
        ScmInfo scmInfo = new ScmInfo("TEST001", "Git", "https://github.com/test/repo");
        
        // Then
        assertEquals("TEST001", scmInfo.getAssetCode());
        assertEquals("Git", scmInfo.getScmType());
        assertEquals("https://github.com/test/repo", scmInfo.getRepositoryUrl());
    }

    @Test
    void setters_validValues_properlySetFields() {
        // Given
        ScmInfo scmInfo = new ScmInfo();
        
        // When
        scmInfo.setAssetCode("TEST002");
        scmInfo.setScmType("SVN");
        scmInfo.setRepositoryUrl("https://svn.example.com/repo");
        
        // Then
        assertEquals("TEST002", scmInfo.getAssetCode());
        assertEquals("SVN", scmInfo.getScmType());
        assertEquals("https://svn.example.com/repo", scmInfo.getRepositoryUrl());
    }
}