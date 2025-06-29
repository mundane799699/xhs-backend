package com.mundane.mail;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class DateUtilTests {


    @Test
    public void testParse() {
        String dateStr = "2024-09-25";
        LocalDateTime localDateTime = DateUtil.parseLocalDateTime(dateStr, "yyyy-MM-dd");
        log.info("localDateTime: {}", localDateTime);
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        log.info("timeStr: {}", timeStr);
        LocalDateTime next = LocalDateTime.now().plusDays(1);
        log.info("next: {}", next);
    }
}
