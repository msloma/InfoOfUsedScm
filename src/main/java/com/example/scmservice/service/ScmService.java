package com.example.scmservice.service;

import com.example.scmservice.model.ScmInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class ScmService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, ScmInfo> localCache = new ConcurrentHashMap<>();
    private static final String KEY_PREFIX = "scm:";

    public ScmService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        
        // Initialize with sample data if Redis is empty
        if (!redisTemplate.hasKey(KEY_PREFIX + "APP001")) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        addScmInfo(new ScmInfo("APP001", "Git", "https://github.com/org/app001"));
        addScmInfo(new ScmInfo("APP002", "SVN", "https://svn.example.com/app002"));
        addScmInfo(new ScmInfo("APP003", "Mercurial", "https://hg.example.com/app003"));
    }

    public Optional<ScmInfo> getScmInfoByAssetCode(String assetCode) {
        // Try to get from Redis first
        ScmInfo scmInfo = (ScmInfo) redisTemplate.opsForValue().get(KEY_PREFIX + assetCode);
        
        // If not in Redis, try local cache
        if (scmInfo == null) {
            scmInfo = localCache.get(assetCode);
        }
        
        return Optional.ofNullable(scmInfo);
    }

    public ScmInfo addScmInfo(ScmInfo scmInfo) {
        // Store in Redis with expiration
        redisTemplate.opsForValue().set(
            KEY_PREFIX + scmInfo.getAssetCode(), 
            scmInfo, 
            1, 
            TimeUnit.DAYS
        );
        
        // Also store in local cache
        localCache.put(scmInfo.getAssetCode(), scmInfo);
        
        return scmInfo;
    }
}
