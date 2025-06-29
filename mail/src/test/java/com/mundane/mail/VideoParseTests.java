package com.mundane.mail;

import com.mundane.mail.service.VideoParseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class VideoParseTests {
    @Autowired
    private VideoParseService videoParseService;

    @Test
    public void testDouyin() {
        String text = "9.46 rEu:/ 复制打开抖音，看看【老五钓鱼（lure）的作品】电动小马达\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02# 路亚 # 原来钓鱼才是抖音运动... https://v.douyin.com/iJMaAb6w/";
        videoParseService.parseDouyin(text);
    }

    @Test
    public void testParse() {
        String text = "50 独立站锦囊发布了一篇小红书笔记，快来看吧！ \uD83D\uDE06 IzRrphH9uhIwDlM \uD83D\uDE06 http://xhslink.com/7ePmwt，复制本条信息，打开【小红书】App查看精彩内容！";
        videoParseService.parse(text);
    }
}
