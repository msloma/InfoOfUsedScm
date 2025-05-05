package com.example.scmservice.service;

import com.example.scmservice.model.ScmInfo;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ScmService {
    private final Map<String, ScmInfo> scmDatabase = new HashMap<>();
    
    public ScmService() {
        // Initialize with some sample data
        scmDatabase.put("APP001", new ScmInfo("APP001", "Git", "https://github.com/org/app001"));
        scmDatabase.put("APP002", new ScmInfo("APP002", "SVN", "https://svn.example.com/app002"));
        scmDatabase.put("APP003", new ScmInfo("APP003", "Mercurial", "https://hg.example.com/app003"));
    }
    
    public Optional<ScmInfo> getScmInfoByAssetCode(String assetCode) {
        return Optional.ofNullable(scmDatabase.get(assetCode));
    }
    
    public ScmInfo addScmInfo(ScmInfo scmInfo) {
        scmDatabase.put(scmInfo.getAssetCode(), scmInfo);
        return scmInfo;
    }
}