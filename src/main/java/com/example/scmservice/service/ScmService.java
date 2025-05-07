package com.example.scmservice.service;

import com.example.scmservice.model.ScmInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ScmService {
    private static final String SCM_KEY_PREFIX = "scm:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, ScmInfo> localCache = new HashMap<>();
    
    @Autowired
    public ScmService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        
        // Initialize with some sample data if Redis is empty
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(SCM_KEY_PREFIX + "APP001"))) {
            addScmInfo(new ScmInfo("APP001", "Git", "https://github.com/org/app001"));
            addScmInfo(new ScmInfo("APP002", "SVN", "https://svn.example.com/app002"));
            addScmInfo(new ScmInfo("APP003", "Mercurial", "https://hg.example.com/app003"));
        }
    }
    
    public Optional<ScmInfo> getScmInfoByAssetCode(String assetCode) {
        // Try to get from Redis first
        ScmInfo scmInfo = (ScmInfo) redisTemplate.opsForValue().get(SCM_KEY_PREFIX + assetCode);
        
        // If not found in Redis, try local cache (fallback mechanism)
        if (scmInfo == null) {
            scmInfo = localCache.get(assetCode);
        }
        
        return Optional.ofNullable(scmInfo);
    }
    
    public ScmInfo addScmInfo(ScmInfo scmInfo) {
        // Store in Redis with 1 day expiration
        redisTemplate.opsForValue().set(
            SCM_KEY_PREFIX + scmInfo.getAssetCode(), 
            scmInfo, 
            1, 
            TimeUnit.DAYS
        );
        
        // Also update local cache as fallback
        localCache.put(scmInfo.getAssetCode(), scmInfo);
        
        return scmInfo;
    }
}
