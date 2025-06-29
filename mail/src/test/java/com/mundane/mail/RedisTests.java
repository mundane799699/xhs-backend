package com.mundane.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
@Slf4j
public class RedisTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisOperations() {
        redisTemplate.opsForValue().set("test", "test");
        log.info("test = {}", redisTemplate.opsForValue().get("test"));
        log.info("test = {}", redisTemplate.opsForValue().get("test1"));
        log.info("test = {}", redisTemplate.opsForValue().get("test2"));
    }
}
