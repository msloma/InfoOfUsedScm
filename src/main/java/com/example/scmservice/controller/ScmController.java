package com.example.scmservice.controller;

import com.example.scmservice.model.ScmInfo;
import com.example.scmservice.service.ScmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scm")
public class ScmController {
    
    private final ScmService scmService;
    
    @Autowired
    public ScmController(ScmService scmService) {
        this.scmService = scmService;
    }
    
    @GetMapping("/{assetCode}")
    public ResponseEntity<ScmInfo> getScmInfo(@PathVariable String assetCode) {
        return scmService.getScmInfoByAssetCode(assetCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ScmInfo> addScmInfo(@RequestBody ScmInfo scmInfo) {
        return ResponseEntity.ok(scmService.addScmInfo(scmInfo));
    }
}