package com.mundane.mail;

import com.mundane.mail.entity.RedBookCollectParseTaskEntity;
import com.mundane.mail.mapper.RedBookCollectParseTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ParseTaskTests {
    @Autowired
    private RedBookCollectParseTaskMapper redBookParseTaskMapper;

    @Test
    public void testQuery() {
        RedBookCollectParseTaskEntity entity = redBookParseTaskMapper.selectByPrimaryKey("222");
        log.info("{}", entity);
    }

    @Test
    public void testAddOrUpdate() {
        RedBookCollectParseTaskEntity entity = new RedBookCollectParseTaskEntity();
        entity.setUserId("111111");
        entity.setStatus(1);
        entity.setProgress("234/456");
        redBookParseTaskMapper.addOrUpdate(entity);
    }
}
