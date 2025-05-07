package com.example.scmservice.controller;

import com.example.scmservice.exception.ResourceNotFoundException;
import com.example.scmservice.model.ScmInfo;
import com.example.scmservice.service.ScmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/scm")
public class ScmController {

    private final ScmService scmService;

    public ScmController(ScmService scmService) {
        this.scmService = scmService;
    }

    @GetMapping("/{assetCode}")
    public ResponseEntity<ScmInfo> getScmInfo(@PathVariable String assetCode) {
        return ResponseEntity.ok(scmService.getScmInfoByAssetCode(assetCode)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", "assetCode", assetCode)));
    }

    @PostMapping
    public ResponseEntity<ScmInfo> addOrUpdateScmInfo(@Valid @RequestBody ScmInfo scmInfo) {
        ScmInfo savedInfo = scmService.addScmInfo(scmInfo);
        return ResponseEntity.ok(savedInfo);
    }
}
