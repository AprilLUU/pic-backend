package com.luu.picbackend.manager;

import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class CacheManager {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final Cache<String, String> LOCAL_CACHE =
            Caffeine.newBuilder().initialCapacity(1024)
                    // 最大10000条数据
                    .maximumSize(10000L)
                    // 缓存 5 分钟移除
                    .expireAfterWrite(5L, TimeUnit.MINUTES)
                    .build();

    private String cacheKey;

    public String buildCacheKey(Object queryRequest, String prefix) {
        // 构建缓存 key
        String queryCondition = JSONUtil.toJsonStr(queryRequest);
        // MD5压缩
        String hashKey = DigestUtils.md5DigestAsHex(queryCondition.getBytes());
        cacheKey = prefix + hashKey;
        return this.cacheKey;
    }

    public String getCache() {
        // 1. 查询本地缓存（Caffeine)
        String cachedValue = LOCAL_CACHE.getIfPresent(cacheKey);
        if (cachedValue != null) return cachedValue;
        // 2. 查询分布式缓存（Redis）
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        cachedValue = valueOps.get(cacheKey);
        if (cachedValue != null) {
            // 如果命中 Redis，存入本地缓存并返回
            LOCAL_CACHE.put(cacheKey, cachedValue);
            return cachedValue;
        }

        return null;
    }

    public void setCache(String cacheValue) {
        // 更新本地缓存
        LOCAL_CACHE.put(cacheKey, cacheValue);
        // 更新 Redis 缓存，设置过期时间为 5 分钟
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(cacheKey, cacheValue, 5, TimeUnit.MINUTES);
    }
}
