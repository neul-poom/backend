package com.jk.module_coupon.coupon.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.util.Collections;

@Repository
public class CouponCountRepository {
    private final RedisTemplate<String, Long> redisTemplate;

    public CouponCountRepository(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long increment(Long couponId) {
        String key = "coupon_count:" + couponId;
        return redisTemplate.opsForValue().increment(key, 1);
    }

    public Long getCount(Long couponId) {
        String key = "coupon_count:" + couponId;
        Long count = redisTemplate.opsForValue().get(key);
        return count != null ? count : 0L;
    }
}
