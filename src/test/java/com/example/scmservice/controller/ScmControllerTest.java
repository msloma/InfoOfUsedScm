package com.example.scmservice.controller;

import com.example.scmservice.model.ScmInfo;
import com.example.scmservice.service.ScmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScmController.class)
class ScmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ScmService scmService;

    @Test
    void getScmInfo_existingAssetCode_returnsScmInfo() throws Exception {
        // Given
        String assetCode = "APP001";
        ScmInfo scmInfo = new ScmInfo(assetCode, "Git", "https://github.com/org/app001");
        when(scmService.getScmInfoByAssetCode(assetCode)).thenReturn(Optional.of(scmInfo));

        // When & Then
        mockMvc.perform(get("/api/scm/{assetCode}", assetCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetCode").value(assetCode))
                .andExpect(jsonPath("$.scmType").value("Git"))
                .andExpect(jsonPath("$.repositoryUrl").value("https://github.com/org/app001"));
    }

    @Test
    void getScmInfo_nonExistingAssetCode_returnsNotFound() throws Exception {
        // Given
        String assetCode = "NONEXISTENT";
        when(scmService.getScmInfoByAssetCode(assetCode)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/scm/{assetCode}", assetCode))
                .andExpect(status().isNotFound());
    }

    @Test
    void addScmInfo_validScmInfo_returnsCreatedScmInfo() throws Exception {
        // Given
        ScmInfo scmInfo = new ScmInfo("APP004", "Git", "https://github.com/org/app004");
        when(scmService.addScmInfo(any(ScmInfo.class))).thenReturn(scmInfo);

        // When & Then
        mockMvc.perform(post("/api/scm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"assetCode\":\"APP004\",\"scmType\":\"Git\",\"repositoryUrl\":\"https://github.com/org/app004\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assetCode").value("APP004"))
                .andExpect(jsonPath("$.scmType").value("Git"))
                .andExpect(jsonPath("$.repositoryUrl").value("https://github.com/org/app004"));
    }
}
