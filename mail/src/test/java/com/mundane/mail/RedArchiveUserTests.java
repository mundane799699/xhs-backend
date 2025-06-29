package com.mundane.mail;


import com.mundane.mail.entity.RedArchiveUser;
import com.mundane.mail.mapper.RedArchiveUserMapper;
import com.mundane.mail.vo.RegisterRequest;
import com.mundane.mail.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
public class RedArchiveUserTests {

    @Autowired
    private RedArchiveUserMapper redArchiveUserMapper;

    @Autowired
    private AuthService registerService;

    @Test
    public void testInsert() {
        RedArchiveUser user = new RedArchiveUser();
        user.setUserName("test");
        user.setEmail("test");
        user.setPassword("test");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setMembershipType("test");
        user.setMembershipExpiry(LocalDateTime.now());
        redArchiveUserMapper.insert(user);
    }
    @Test
    public void testFindByEmail() {
        RedArchiveUser user = redArchiveUserMapper.findByEmail("799699348@qq.com");
        log.info("user = {}", user);
    }

    @Test
    public void testRegister() {
        RegisterRequest vo = new RegisterRequest();
        vo.setEmail("1470685713@qq.com");
        vo.setUserName("喵喵");
        vo.setPassword("123456");
        registerService.register(vo);
    }
}
