package com.mundane.mail;

import com.mundane.mail.entity.WxUserEntity;
import com.mundane.mail.mapper.WxUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
public class WxAuthServiceTests {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Test
    public void testDb() {
        String openId = "1111";
        String unionId = "2222";
        String sessionKey = "abcdefg";
        WxUserEntity user = wxUserMapper.findByOpenId(openId);
        if (user == null) {
            user = new WxUserEntity();
            user.setUnionid(unionId);
            user.setOpenid(openId);
            user.setSessionKey(sessionKey);
            wxUserMapper.insertSelective(user);
        } else {
            user.setUpdateTime(new Date());
            wxUserMapper.updateByPrimaryKeySelective(user);
        }
    }
}
